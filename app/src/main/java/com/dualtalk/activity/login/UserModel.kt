package com.dualtalk.activity.login

class UserModel(email: String, password: String, name: String) {
   var email: String
    get() = this.email
        set(value) {email = value}
    var password: String
        get() =this.password
        set(value) {password= value}
    var name: String
        get() =this.name
        set(value) {name = value}

    constructor(email: String, password: String) : this(email, password, "Unknown") {
        this.email = email
        this.password = password
    }

    init {
        this.email = email
        this.password = password
        this.name = name
    }
}
