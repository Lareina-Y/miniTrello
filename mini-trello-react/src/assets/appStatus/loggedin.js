// src/assets/appStatus/loggedin.js
import SharedReducer from 'shared-reducer-hooks';

const initialState = {
  logged: false,
  user_id: 0,
};
const [mapState, dispatch] = SharedReducer((state = initialState, action) => {
  switch(action.type) {
    case 'login':
      return { ...state, logged: true };
    case 'logout':
      return { ...state, logged: false };
    case 'userId':
      return { ...state, user_id: action.id };
    default:
      return state;
  }
});

export const useLogged = mapState((state) => state.logged);
export const useLogout = mapState((state) => state.logged);
export const userID = mapState((state) => state.user_id);

export const loginAction = () => dispatch({ type: 'login' });
export const logoutAction = () => dispatch({ type: 'logout' });
export const userIdAction = (id) => dispatch({ type: 'userId', id });