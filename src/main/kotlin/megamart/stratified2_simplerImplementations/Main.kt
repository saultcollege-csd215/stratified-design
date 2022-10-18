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

    return if ( item === null ) {
        cart
    } else {
        updateItem(cart, item, setPrice(item, price))
    }
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

    // But after writing getItemBy name, we can recognize that lookup-by-name pattern again
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

fun updateItem(cart: List<CartItem>, oldItem: CartItem, newItem: CartItem): List<CartItem> {
    val copy = cart.toMutableList()
    val i = copy.indexOf(oldItem)
    copy[i] = newItem
    return copy

    // Or: return cart.toMutableList()[cart.indexOf(oldItem)] = newItem
}


/**
 * Basic item operations
 */

fun setPrice(item: CartItem, price: Double): CartItem {
    return item.copy(price=price)
}
