import { reactive } from 'vue'
import cookies from 'vue-cookies';

export const store = reactive({
    isLogedIn: false,
    username: '未登录',
    userId: 0,
    logIn(id, name) {
        this.isLogedIn = true
        this.username = name
        this.userId = id
        console.log(this.isLogedIn, this.userId, this.username);
    },
    logOut() {
        this.isLogedIn = false
        this.username = '未登录'
        this.userId = 0
        console.log(this.isLogedIn, this.userId, this.username);
        cookies.remove('JSESSIONID')
    },

})