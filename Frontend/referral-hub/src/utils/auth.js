import { jwtDecode } from 'jwt-decode';  // Correct import
 
export function getRolesFromToken(token) {
    if (!token) return [];
  const decoded = jwtDecode(token);
  return decoded.roles || [];
}

export function getEmpId(token){
  if (!token) return null;
  const decoded = jwtDecode(token);
  return decoded.EmpId;
}

export function getEmpName(token){
  if (!token) return null;
  const decoded = jwtDecode(token);
  return decoded.EmpName;
}
 
export function isAuthenticated() {
  const token = localStorage.getItem('token');
  return !!token;
}