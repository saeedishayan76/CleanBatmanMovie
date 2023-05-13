package com.shayan.composeBatmanMovie.constant

sealed class NavItem(val route: String) {
    object Home: NavItem("Home")
    object Detail: NavItem("Detail")
}
