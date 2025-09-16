export const MyCartReducer = (state, action) => {
    switch (action.type) {
        case "add":
            if (state.some(item => item.slotId === action.payload.slotId)) {
                return state;
            }
            return [...state, action.payload];
        default:
            return state;
    }
}

