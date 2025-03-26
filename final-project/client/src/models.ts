export interface Recipe {
    recipeId: number
    title: string
    imageUrl: string
}

export interface Information {
    recipeId: number
    title: string
    imageUrl: string
    ingredients: Ingredient[]
    instructions: Instruction[]
}

export interface Ingredient {
    id: number
    category: string
    name: string
    imageUrl: string
    amount: number
    unit: string
}

export interface Instruction {
    step: number
    instruction: string
    equipmentName: string
}

export interface IngredientSlice {
    selectedIngredients: Ingredient[]
}

export interface Product {
    productId: number
    productName: string
    price: number
    displayUnit: string
    imageUrl: string
    quantity?: number
}

export interface CartSlice {
    items: Product[]
}

export interface Order {
    orderId: number
    name: string
    email: string
    address: string
    phone: string
    cart: CartSlice
    totalAmount: number
}
