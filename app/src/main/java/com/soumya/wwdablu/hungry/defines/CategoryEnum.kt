package com.soumya.wwdablu.hungry.defines

import com.soumya.wwdablu.hungry.R

enum class CategoryEnum(val id: Int) {
    Delivery(1) {
        override fun icon() : Int {
            return R.drawable.ic_delivery
        }},
    Dineout(2) {
        override fun icon() : Int {
            return R.drawable.ic_dining
        }},
    Nightlife(3) {
        override fun icon() : Int {
            return R.drawable.ic_nightlife
        }},
    CatchingUp(4) {
        override fun icon() : Int {
            return R.drawable.ic_pub
        }},
    TakeAway(5) {
        override fun icon() : Int {
            return R.drawable.ic_takeout
        }},
    Cafes(6) {
        override fun icon() : Int {
            return R.drawable.ic_cafe
        }},
    DailyMenus(7) {
        override fun icon() : Int {
            return R.drawable.ic_dining
        }},
    Breakfast(8) {
        override fun icon() : Int {
            return R.drawable.ic_breakfast
        }},
    Lunch(9) {
        override fun icon() : Int {
            return R.drawable.ic_lunch
        }},
    Dinner(10) {
        override fun icon() : Int {
            return R.drawable.ic_dinner
        }},
    PubAndBar(11) {
        override fun icon() : Int {
            return R.drawable.ic_pub
        }},
    PocketFriendlyDelivery(13) {
        override fun icon() : Int {
            return R.drawable.ic_delivery
        }},
    ClubAndLounge(14)  {
        override fun icon() : Int {
            return R.drawable.ic_pub
        }},
    Recommended(999)  {
        override fun icon() : Int {
            return R.drawable.ic_recommended
        }};

    abstract fun icon() : Int
}