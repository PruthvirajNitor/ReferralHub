<template>
  <br>
  <form @submit.prevent class="custom-form">
    <div class="mb-3">
      <label for="exampleInputEmail" class="form-label">Email</label>
      <input type="email" class="form-control" id="exampleInputEmail" v-model="email" required>
    </div>
    <div class="mb-3">
      <label for="exampleInputPassword" class="form-label">Password</label>
      <input type="password" class="form-control" id="exampleInputPassword" v-model="password" required>
    </div>
    <div class="button-container">
      <button type="submit" class="btn btn-dark" @click="handleSubmit">SignIn</button>
    </div>
    <span class="form-message">Not a user yet? <a href="#" @click.prevent="toRegister">Register here</a></span>
  </form>
  <br>
</template>

<script>

import api from '@/utils/Axios';
import store from '@/store';
import { getEmpName, getRolesFromToken } from '@/utils/auth';
import { useToast } from 'vue-toastification';

export default {
  data() {
    return {
      email: '',
      password: ''
    };
  },
  methods: {
    async handleSubmit() {
      try {
        const response = await api.post('/user/login', {
          email: this.email,
          password: this.password,
          
        });
        const token = response.data.jwtToken;

        console.log("token is--->",token);
        store.setToken(token);
        console.log("roles are--->",getRolesFromToken(token));
        console.log(getEmpName(token));

        const toast = useToast();
        const notify = () => {
        toast.success('Welcome !');
        };

        if(getRolesFromToken(token) === 'ROLE_ADMIN')        
        this.$router.push('/emps/approved');
        if(getRolesFromToken(token) === 'ROLE_EMPLOYEE')        
        this.$router.push('/emp/joblist');
        if(getRolesFromToken(token) === 'ROLE_HR')        
        this.$router.push('/jobs');

        notify();
      
      } catch (error) {
        const toast = useToast();
        const notify = () => {
        toast.error('You are not approved yet. Wait for approval!');
        };
        notify();
        this.error = 'Invalid email or password';
      }
    },
    toRegister() {
      this.$router.push('/register'); // Redirect to the registration page
    }
  }
};
</script>

<style scoped>
.custom-form {
  border: 1px solid #ddd;
  border-radius: 10px;
  max-width: 400px; /* Adjust max-width as needed */
  margin: auto; /* Center the form horizontally */
  padding: 20px;
  background-color: #f9f9f9;
}

.custom-form .mb-3 {
  margin-bottom: 1rem; /* Adjust spacing between form fields */
}

.button-container {
  display: flex;
  justify-content: center; /* Center the button horizontally */
  margin-top: 1rem; /* Adjust margin as needed */
}

.form-message {
  font-size: 0.875rem; /* Slightly smaller text size */
  color: #666;
  display: block;
  text-align: center; /* Center the text */
  margin-top: 1rem; /* Space above the message */
}

.form-message a {
  color: #007bff; /* Link color */
  text-decoration: none; /* Remove underline */
}

.form-message a:hover {
  text-decoration: underline; /* Underline on hover */
}

@media (max-width: 576px) {
  .custom-form {
    max-width: 100%; /* Full width on small screens */
    margin-left: 10px; /* Adjust margin for smaller screens */
    margin-right: 10px;
  }
}
</style>
