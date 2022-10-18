package megamart.stratified1_complexImplementations

import megamart.*

/**
 *  Business rules about carts
 */

fun getsFreeShipping(cart: List<CartItem>) =  calcTotal(cart) >= 20

fun cartTax(cart: List<CartItem>) = calcTax(calcTotal(cart))

fun freeTieClip(cart: List<CartItem>): List<CartItem> {

    // TODO: After adding the 'isInCart' function to our 'cart operations' layer, this code can be simplified!
    var hasTie = false
    var hasTieClip = false

    // TODO: Note this pattern of looping through the cart items and finding an item by name; it shows up in several places.
    //      This is an opportunity for encapsulation and generalization!
    for ( item in cart ) {
        if ( item.name == "tie" ) {
            hasTie = true
        } else if ( item.name == "tie clip") {
            hasTie = true
        }
    }

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

// TODO: This function could be simplified if we had a getItemByName function in the 'cart operations' layer
fun removeItemByName(cart: List<CartItem>, name: String): List<CartItem> {

    // TODO: Here's that lookup-by-name pattern again!
    for ( item in cart ) {
        if ( item.name == name ) {
            return cart.minus(item)  // Remove the item if we found it
        }
    }
    return cart // If we get here, the name was not found and the cart remains unchanged
}

fun calcTotal(cart: List<CartItem>): Double {
    var total = 0.0
    for ( item in cart ) {
        total += item.price
    }
    return total
}

fun addItem(cart: List<CartItem>, item: CartItem) = cart + item

fun setPriceByName(cart: List<CartItem>, name: String, price: Double): List<CartItem> {
    // We need to do copy-on-write of the main list because we are changing one of the references
    val copy = cart.toMutableList()
    // TODO: And here it is again in a similar form, but slightly different than the above examples
    for ( i in copy.indices ) {
        if ( copy[i].name == name ) {
            copy[i] = setPrice(copy[i], price)
        }
    }
    return copy
}

// TODO: If we had an 'isInCart' function in this layer, some of our code in higher layers can be simplified!

// TODO: If we had a 'getItemByName' function, some of our code above could be simplified!

// TODO: If we had a 'updateItem' function in this layer, the setPriceByName function can be simplified!

/**
 * Basic item operations
 */

fun setPrice(item: CartItem, price: Double): CartItem {
    // Copy-on-write to keep item immutable
    return item.copy(price=price)
}