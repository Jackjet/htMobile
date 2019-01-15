(function( $ ) {    
			$.widget( 
				"custom.comboboxRemote", {      
					_create: function() {        
						this.wrapper = $( "<span>" ).addClass( "custom-combobox" ).insertAfter( this.element );         
						this.element.hide();        
						this._createAutocomplete();        
						this._createShowAllButton();      
					},       
					_createAutocomplete: function() {        
						var selected = this.element.children( ":selected" ), value = selected.val() ? selected.text() : "";      
						var callback = this.element.attr("onchange") ? this.element.attr("onchange") : ""; 
						var searchURL =  this.element.attr("url") ? this.element.attr("url") : "";
						//alert(searchURL);
						this.input = $( "<input>" ).appendTo( this.wrapper ).val( value ).attr("size","15").attr( "title", "" ).addClass( "custom-combobox-input ui-widget ui-widget-content ui-state-default ui-corner-left" ).autocomplete({
							delay: 0,            
							minLength: 2,            
							source: $.proxy( this, "_source" )
			            }).tooltip({            
			            	tooltipClass: "ui-state-highlight"
			            });         
			            this._on( this.input, {
			            	autocompleteselect: function( event, ui ) {            
			            		ui.item.option.selected = true;           
			            		this._trigger( "select", event, {              
			            			item: ui.item.option            
			            		});    
			            		//alert(callback);
			            		//callback;  
			            		//displayDetail('itemIds');
			            		if(callback != ""){
			            			eval(callback);
			            		}
			            		
			            		//alert(doMethod);
			            		//doMethod; 
			            		
								//var ex = {doCallBack:eval("("+callback+")")};
								//ex.doCallBack();
			            	},           
			            	autocompletechange: "_removeIfInvalid"        
			           });      
			        },       
			        _createShowAllButton: function() {        
			        	var input = this.input,          
			        	wasOpen = false;         
			        	$( "<a>" ).attr( "tabIndex", -1 ).attr( "title", "查看所有下拉选项" ).tooltip().appendTo( this.wrapper ).button({            
			        		icons: {              
			        			primary: "ui-icon-triangle-1-s"            
			        		},            
			        		text: false         
			        	}).removeClass( "ui-corner-all" ).addClass( "custom-combobox-toggle ui-corner-right" ).mousedown(function() {            
			        		wasOpen = input.autocomplete( "widget" ).is( ":visible" );          
			        	}).click(function() {            
			        		input.focus();             
			        		// Close if already visible            
			        		if ( wasOpen ) {              
			        			return;            
			        		}             
			        		// Pass empty string as value to search for, displaying all results            
			        		input.autocomplete( "search", "" );          
			        	});      
			        },       
			        _source: function( request, response ) {    
			        	/*var term = request.term;        
			        	if ( term in cache ) {          
			        		response( cache[ term ] );          
			        		return;        
			        	}         
			        	$.getJSON( "search.php", request, function( data, status, xhr ) {          
			        		cache[ term ] = data;          
			        		response( data );        
			        	});*/
			        
			        	
			        	//$("#wrap").mask("搜索中，请稍等...");
			        	$.getJSON("/stock/itemInfor.htm?method=getItemsByTagName&itemTag="+encodeURI(request.term),function(data) {
					           var options = "";
					           //alert("11"+data._Items);
					           $.each(data._Items, function(i, n) {
					               //options += "<option value='"+n.supplier.supplierId+"'  class='"+n.restCount+"'>"+n.supplier.supplierName+"</option>";
					               options += "<option value='"+n.itemId+"' class='"+n.itemUnit+"|"+n.itemPrice+"|"+n.priceUnit+"|"+n.count+"'>【"+n.itemCode+"】"+n.itemName+"</option>";
					               //+"|"+n.inOrderItemId+"   
					           });   
					           //alert($("#itemId").html());
					           //alert(options);
					           $("#itemId").html(options);   
					           //$("#itemId").append(options);
					        }   
					    );
					    //$("#wrap").unmask();
			        	    
			        	var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );        
			        	response( this.element.children( "option" ).map(function() {          
			        		var text = $( this ).text();          
			        		if ( this.value && ( !request.term || matcher.test(text) ) )            
			        			return {              
			        				label: text,              
			        				value: text,              
			        				option: this            
			        			};        
			        	}) );      
			        },       
			        _removeIfInvalid: function( event, ui ) {         
				        // Selected an item, nothing to do        
				        if ( ui.item ) {          
				        	return;        
				        }         
				        // Search for a match (case-insensitive)        
				        var value = this.input.val(),valueLowerCase = value.toLowerCase(), valid = false;        
				        this.element.children( "option" ).each(function() {          
				        	if ( $( this ).text().toLowerCase() === valueLowerCase ) {            
				        		this.selected = valid = true;            
				        		return false;          
				        	}        
				        });         
				        // Found a match, nothing to do        
				        if ( valid ) {          
				        	return;        
				        }         
				        // Remove invalid value        
				        //this.input.val( "" ).attr( "title", value + " 不是有效值" ).tooltip( "open" );        
				        this.element.val( "" );        
				        
				        var callback = this.element.attr("onchange") ? this.element.attr("onchange") : "";  
				        //alert(callback);
				        if(callback != ""){
				        	eval(callback);
				        }
				        
				        this._delay(function() {
				        	this.input.tooltip( "close" ).attr( "title", "" );        
				        }, 2500 );        
				        this.input.autocomplete( "instance" ).term = "";      
			        },       
			        _destroy: function() {        
			        	this.wrapper.remove();        
			        	this.element.show();      
			        }    
			     });  
			 })( jQuery );