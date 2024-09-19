<template>
  <form @submit.prevent="toSignIn" class="custom-form">
    <div class="mb-3">
      <label for="firstName" class="form-label">First Name</label>
      <input type="text" class="form-control" id="firstName" v-model="candidate.firstName" >
    </div>
    <div class="mb-3">
      <label for="lastName" class="form-label">Last Name</label>
      <input type="text" class="form-control" id="lastName" v-model="candidate.lastName" required>
    </div>
    <div class="mb-3">
      <label for="email" class="form-label">Email</label>
      <input type="email" class="form-control" id="email" v-model="candidate.email" required>
      <p v-if="emailError" class="text-danger">{{ emailError }}</p>
    </div>
    <div class="mb-3">
      <label for="mobileInput" class="form-label">Mobile No</label>
      <input type="tel" class="form-control" id="mobileInput" v-model="candidate.mobileNum" required>
      <p v-if="mobileError" class="text-danger">{{ mobileError }}</p>
    </div>
    <div class="mb-3">
      <label for="salaryInput" class="form-label">Salary</label>
      <input type="number" class="form-control" id="salaryInput" v-model="candidate.salary" required>
    </div>
    <div class="mb-3">
      <label for="roleSelect" class="form-label">Role</label>
      <select class="form-control" id="roleSelect" v-model="candidate.role" required>
        <option value="" disabled>Select a role</option>
        <option value="HR">HR</option>
        <option value="EMPLOYEE">Employee</option>
      </select>
    </div>
    <div class="mb-3">
      <label for="departmentInput" class="form-label">Department</label>
      <input type="text" class="form-control" id="departmentInput" v-model="candidate.department" required>
    </div>
    <div class="mb-3">
      <label for="passwordInput" class="form-label">Password</label>
      <input type="password" class="form-control" id="passwordInput" v-model="candidate.password" required>
    </div>
    <div class="mb-3">
      <label for="fileInput" class="form-label">Upload File</label>
      <input type="file" class="form-control" id="fileInput" @change="handleFileChange">
    </div>
    <div class="mb-3">
      <button type="submit" class="btn btn-dark">Submit</button>
      <button type="button" class="btn btn-dark" @click="$router.push('/signin')">Sign In</button>
    </div>
  </form>
</template>

<script>
import api from '@/utils/Axios'; // Ensure this path is correct for your project
import { useToast } from 'vue-toastification';

export default {
  data() {
    return {
      candidate: {
        firstName: '',
        lastName: '',
        email: '',
        mobileNum: '',
        salary: '',
        department: '',
        role: '',
        password: ''
      },
      file: null, // Initialize file property
      mobileError: '', // Add property for mobile number validation error
      emailError: '' // Add property for email validation error
    };
  },
  methods: {
    handleFileChange(event) {
      // Ensure a file is selected
      if (event.target.files.length) {
        this.file = event.target.files[0];
      }
    },
    validateMobileNumber(mobileNum) {
      // Check if mobile number is exactly 10 digits
      const regex = /^\d{10}$/;
      return regex.test(mobileNum);
    },
    validateEmail(email) {
      // Check if email ends with @nitorinfotech.com
      const regex = /^[^\s@]+@nitorinfotech\.com$/;
      return regex.test(email);
    },
    async toSignIn() {
      // Clear previous error messages
      this.mobileError = '';
      this.emailError = '';

      // Validate mobile number
      if (!this.validateMobileNumber(this.candidate.mobileNum)) {
        this.mobileError = 'Mobile number must be exactly 10 digits';
        return; // Stop the form submission
      }

      // Validate email
      if (!this.validateEmail(this.candidate.email)) {
        this.emailError = 'Email must end with @nitorinfotech.com';
        return; // Stop the form submission
      }

      const formData = new FormData();
      
      // Append form data
      Object.keys(this.candidate).forEach(key => {
        formData.append(key, this.candidate[key]);
      });

      // Append file if selected
      if (this.file) {
        formData.append('file', this.file);
      }

      try {
        const response = await api.post('/signup/addUser', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        });

        console.log('Response:', response.data);
        
        const toast = useToast();
        toast.success('Registration success, wait for approval!');

        this.$router.push('/signin');
      } catch (error) {
        console.error('Error adding employee:', error.response?.data || error.message);
      }
    }
  }
};
</script>

<style scoped>
.custom-form {
  border: 1px solid #ddd;
  border-radius: 10px;
  max-width: 600px; /* Adjust max-width as needed */
  margin: auto; /* Center the form horizontally */
  padding: 20px;
  background-color: #f9f9f9;
}

.custom-form .btn {
  margin-right: 10px; /* Add spacing between buttons */
}

@media (max-width: 576px) {
  .custom-form {
    max-width: 100%; /* Full width on small screens */
    margin: 10px; /* Adjust margin for smaller screens */
  }
}

.text-danger {
  color: red; /* Style for error messages */
}
</style>
