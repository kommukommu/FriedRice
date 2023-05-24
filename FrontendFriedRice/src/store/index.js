import { reactive } from 'vue'
import cookies from 'vue-cookies';

export const store = reactive({
    isOnline: false,
    username: "未登录",
    userId: 0,
    logIn(id, name) {
        this.isOnline = true
        this.username = name
        this.userId = id
        console.log(this.isOnline, this.userId, this.username);
    },
    logOut() {
        this.isOnline = false
        this.username = '未登录'
        this.userId = 0
        console.log(this.isOnline, this.userId, this.username);
        cookies.remove('JSESSIONID')
    },
    isSearchByConditions: false,
    startSearchByConditions() {
        // this.isSearchByConditions = false
        this.isSearchByConditions = true
    },
    stopSearchByConditions() {
        // this.isSearchByConditions = true
        this.isSearchByConditions = false
    },
    searchingConditions: "搜索项目名称",
})