(function () {
	var $ax = function (url, success, error, async) {
		this.url = url;
		this.type = "post";
		this.data = {};
		this.dataType = "json";
		this.async = false;
		this.success = success;
		this.error = error;
        this.contentType = "application/x-www-form-urlencoded;charset=UTF-8";
    };
	
	$ax.prototype = {
		start : function () {
			var me = this;
			
			if (this.url.indexOf("?") == -1) {
				this.url = this.url + "?jstime=" + new Date().getTime();
			} else {
				this.url = this.url + "&jstime=" + new Date().getTime();
			}

			$.ajax({
		        type: this.type,
		        url: this.url,
		        dataType: this.dataType,
		        async: this.async,
		        data: this.data,
			    contentType: this.contentType,
				beforeSend: function(data) {
					
				},
		        success: function(data) {
		        	me.success(data);
		        },
		        error: function(data) {
		        	me.error(data);
		        }
		    });
		}, 
		
		set : function (key, value) {
			if (typeof key == "object") {
				for (var i in key) {
					if (typeof i == "function")
						continue;
					this.data[i] = key[i];
				}
			} else {
				this.data[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
			}
			return this;
		},
		
		setData : function(data){
			if(this.isJSON(data)){
                this.contentType = "application/json;charset=UTF-8";
			}
			this.data = data;
			return this;
		},
		
		clear : function () {
			this.data = {};
			return this;
		},

		isJSON : function (str) {
            if (typeof str == 'string') {
                try {
                    var obj=JSON.parse(str);
                    if(str.indexOf('{')>-1){
                        return true;
                    }else{
                        return false;
                    }

                } catch(e) {
                    console.log(e);
                    return false;
                }
            }
            return false;
        }
	};
	
	window.$ax = $ax;
	
} ());