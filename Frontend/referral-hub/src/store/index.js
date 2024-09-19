import { reactive } from 'vue';
import { getRolesFromToken, getEmpId, getEmpName } from '../utils/auth';

const state = reactive({
  token: localStorage.getItem('token') || null,
  roles: getRolesFromToken(localStorage.getItem('token')) || [],
  userId: getEmpId(localStorage.getItem('token')) || null,
  EmpName: getEmpName(localStorage.getItem('token')) || null, 
});

const setToken = (token) => {
  state.token = token;
  state.roles = getRolesFromToken(token);
  state.userId = getEmpId(token);
  state.EmpName = getEmpName(token);
  localStorage.setItem('token', token);
  
  // Fetch user name after setting the token
  // fetchUserName();
};

const clearToken = () => {
  state.token = null;
  state.roles = [];
  state.userId = null;
  state.userName = ''; // Clear user name
  state.EmpName = '';
  localStorage.removeItem('token');
};

// const fetchUserName = async () => {
//   if (state.userId) {
//     try {
//       const response = await fetch(`/employee/getName/${state.userId}`);
//       if (response.ok) {
//         const data = await response.json();
//         state.userName = data.firstName || ''; // Update userName in state
//       } else {
//         console.error('Failed to fetch user name');
//       }
//     } catch (error) {
//       console.error('Error fetching user name:', error);
//     }
//   }
// };

export default {
  state,
  setToken,
  clearToken,
  // fetchUserName,
};
