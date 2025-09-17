export const MyCartReducer = (state, action) => {
    switch (action.type) {
        case "add":
            if (state.some(item => item.appointmentSlot.id === action.payload.appointmentSlot.id)) {
                return state;
            }
            return [...state, action.payload];
        default:
            return state;
    }
}