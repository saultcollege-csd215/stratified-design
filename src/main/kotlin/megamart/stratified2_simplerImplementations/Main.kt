package megamart.stratified2_simplerImplementations

import megamart.*

/**
 *  Business rules about carts
 */

fun getsFreeShipping(cart: List<CartItem>) =  calcTotal(cart) >= 20

fun cartTax(cart: List<CartItem>) = calcTax(calcTotal(cart))

fun freeTieClip(cart: List<CartItem>): List<CartItem> {
    val hasTie = isInCart(cart, "tie")
    val hasTieClip = isInCart(cart, "tie clip")

    if ( hasTie && !hasTieClip ) {
        return addItem(cart, CartItem("tie clip", 0.0))
    }
    return cart
}

/**
 * General business rules
 */

fun calcTax(total: Double) = total * 0.13

/**
 * Basic cart operations
 */

fun calcTotal(cart: List<CartItem>): Double {
    var total = 0.0
    for ( item in cart ) {
        total += item.price
    }
    return total
}

fun removeItemByName(cart: List<CartItem>, name: String): List<CartItem> {
    val item = getItemByName(cart, name)
    return if (item === null) cart else cart.minus(item)
}

fun addItem(cart: List<CartItem>, item: CartItem) = cart + item

fun setPriceByName(cart: List<CartItem>, name: String, price: Double): List<CartItem> {
    val item = getItemByName(cart, name)

    if ( item === null ) {
        return cart
    }

    // Since we are updating a CartItem object inside the list, need to copy the CartItem to keep cart immutable
    // (Copy-on-write)
    return replaceListItem(cart, item, setPrice(item, price))
}

fun isInCart(cart: List<CartItem>, name: String): Boolean {
    // At first, we might write this function like this:
    /*
    for ( item in cart ) {
        if ( item.name == name ) {
            return true
        }
    }
    return false
     */

    // But after writing getItemByName, we can recognize that lookup-by-name pattern again
    // and reuse it to perform the necessary calculation here!
    return getItemByName(cart, name) !== null
}

fun getItemByName(cart: List<CartItem>, name: String): CartItem? {
    for ( item in cart ) {
        if ( item.name == name ) {
            return item
        }
    }
    return null
}


/**
 * Basic item operations
 */

fun setPrice(item: CartItem, price: Double): CartItem {
    return item.copy(price=price)
}

/**
 * Generic list operations
 */

/* An implementation using loops */
//fun <I> replaceListItem(list: List<I>, oldItem: I, newItem: I): List<I> {
//    val copy = list.toMutableList()
//    for ( i in copy.indices ) {
//        if ( copy[i] == oldItem ) {   // This is STRUCTURAL comparison in Kotlin (like Java's .equals and Python's ==)
//            copy[i] = newItem
//            break                     // If we get here we're done looping
//        }
//    }
//    return copy
//}

fun <I> replaceListItem(list: List<I>, oldItem: I, newItem: I) =
    when (val i = list.indexOf(oldItem)) {
        // -1 means the oldItem was not found and the list remains unchanged
        -1 -> list

        // Copy-on-write because we are updating one of the list's elements; need to keep list immutable
        else -> list.slice(0 until i) + newItem + list.slice(i until list.size)
    }

