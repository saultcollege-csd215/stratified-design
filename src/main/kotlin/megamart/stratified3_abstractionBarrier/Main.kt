package megamart.stratified3_abstractionBarrier

import megamart.*

/**
 *  Business rules about carts
 *
 *
 *  NOTE: With a good abstraction barrier, the implementation of the cart can be completely changed without effecting
 *  code that uses the abstraction barrier layer!
 */

fun getsFreeShipping(cart: Cart) =  calcTotal(cart) >= 20

fun cartTax(cart: Cart) = calcTax(calcTotal(cart))

fun freeTieClip(cart: Cart): Cart {
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
 *
 * NOTE: This layer is an abstraction barrier.  Code above here will not need to change, even if the implementation
 *      of data in this layer, such as the cart, changes completely
 */

// typealias Cart = List<String, CartItem>   // LIST implementation
typealias Cart = Map<String, CartItem>  // MAP implementation

fun calcTotal(cart: Cart): Double {
    var total = 0.0

    /* LIST implementation */
//    for ( item in cart ) {
//        total += item.price
//    }

    /* MAP implementation */
    for ( item in cart.values ) {
        total += item.price
    }

    return total
}

fun removeItemByName(cart: Cart, name: String): Cart {
    val item = getItemByName(cart, name)
    // return if (item === null) cart else cart.minus(item)  // LIST implementation
    return cart.minus(name)  // MAP implementation
}

fun addItem(cart: Cart, item: CartItem) =
    // cart + item // LIST implementation
    cart + (item.name to item)  // MAP implementation

fun setPriceByName(cart: Cart, name: String, price: Double): Cart {
    val item = getItemByName(cart, name)

    return if ( item === null ) {
        cart
    } else {
        // updateItem(cart, item, setPrice(item, price))  // LIST implementation
        updateItem(cart, name, setPrice(item, price)) // MAP implementation
    }
}

fun isInCart(cart: Cart, name: String): Boolean {
    return getItemByName(cart, name) !== null
}

fun getItemByName(cart: Cart, name: String): CartItem? {
    /* LIST implementation */
//    for ( item in cart ) {
//        if ( item.name == name ) {
//            return item
//        }
//    }
//    return null

    return cart[name] // MAP implementation
}

/* LIST implementation */
//fun updateItem(cart: Cart, oldItem: CartItem, newItem: CartItem): Cart {
//    val copy = cart.toMutableList()
//    val i = copy.indexOf(oldItem)
//    copy[i] = newItem
//    return copy
//}

/* MAP implementation */
fun updateItem(cart: Cart, name: String, newItem: CartItem) = cart + mapOf(name to newItem)


/**
 * Basic item operations
 */

fun setPrice(item: CartItem, price: Double): CartItem {
    return item.copy(price=price)
}
