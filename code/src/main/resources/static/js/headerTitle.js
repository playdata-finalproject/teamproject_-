new Vue({
    el: '#language',
    data:{
        categories: []
    },
    methods : {
        languageboard : function (){
            let self = this;
            axios.get('http://localhost:8080/category/title/1')
                .then(function (response){
                    console.log(response)
                    self.categories = response.data
                    console.log(self.categorys)
                })
                .catch(function (error){
                    self.categories = '에러! '+  error
                })
        }
    }
})
new Vue({
    el: '#error',
    data:{
        categories: []
    },
    methods : {
        errorboard : function (){
            let self = this;
            axios.get('http://localhost:8080/category/title/2')
                .then(function (response){
                    console.log(response)
                    self.categories = response.data
                    console.log(self.categorys)
                })
                .catch(function (error){
                    self.categories = '에러! '+  error
                })
        }
    }
})
new Vue({
    el: '#best',
    data:{
        categories: []
    },
    methods : {
        bestboard : function (){
            let self = this;
            axios.get('http://localhost:8080/category/title/3')
                .then(function (response){
                    console.log(response)
                    self.categories = response.data
                    console.log(self.categorys)
                })
                .catch(function (error){
                    self.categories = '에러! '+  error
                })
        }
    }
})
new Vue({
    el: '#employee',
    data:{
        categories: []
    },
    methods : {
        employeeboard : function (){
            let self = this;
            axios.get('http://localhost:8080/category/title/4')
                .then(function (response){
                    console.log(response)
                    self.categories = response.data
                    console.log(self.categorys)
                })
                .catch(function (error){
                    self.categories = '에러! '+  error
                })
        }
    }
})
new Vue({
    el: '#question',
    data:{
        categories: []
    },
    methods : {
        questionboard : function (){
            let self = this;
            axios.get('http://localhost:8080/category/title/5')
                .then(function (response){
                    console.log(response)
                    self.categories = response.data
                    console.log(self.categorys)
                })
                .catch(function (error){
                    self.categories = '에러! '+  error
                })
        }
    }
})
