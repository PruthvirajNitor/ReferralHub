<template>
  <div class="registration-form">
    <h2>Candidate Registration</h2>
    <form @submit.prevent="referCandidate">
      <div class="form-group">
        <label for="name">Name</label>
        <input type="text" id="name" v-model="candidate.name" required autofocus />
      </div>
      <div class="form-group">
        <label for="email">Email</label>
        <input type="email" id="email" v-model="candidate.email" required />
      </div>
      <div class="form-group">
        <label for="mobile">Mobile No</label>
        <input type="tel" id="mobile" v-model="candidate.mobileNum" required />
      </div>
      <div class="form-group">
        <label for="experience">Experience (In years)</label>
        <input type="number" id="experience" v-model="candidate.experience" required />
      </div>
      <div class="form-group">
        <label for="jobId">Job Id</label>
        <input type="number" id="jobId" v-model="candidate.jobId" required readonly />
      </div>
      <div class="form-group">
        <label for="referredBy">Referred By</label>
        <input type="number" id="referredBy" v-model="candidate.referredById" required readonly />
      </div>
      <div class="form-group">
        <label for="resume">Resume</label>
        <input type="file" id="resume" @change="handleFileChange" required />
      </div>
      <div class="form-group button-container">
        <button type="submit" class="btn btn-dark">Register</button>
      </div>
    </form>
  </div>
</template>

<script>
import api from '@/utils/Axios';
import { useToast } from 'vue-toastification';
import { mapState } from 'vuex';

export default {
  data() {
    return {
      candidate: {
        name: '',
        email: '',
        mobileNum: '',
        experience: null,
        jobId: null,
        referredById: null
      },
      file: null
    };
  },
  computed: {
    ...mapState(['EmpId']) // Maps EmpId from Vuex store to the component
  },
  mounted() {
    // Extract jobId from query parameters and set it
    this.candidate.jobId = this.$route.query.jobId || '';
    this.candidate.referredById = this.EmpId; // Set referredById from Vuex store
  },
  methods: {
    handleFileChange(event) {
      this.file = event.target.files[0];
    },
    async referCandidate() {
      const formData = new FormData();
      
      // Append form data
      formData.append('name', this.candidate.name);
      formData.append('email', this.candidate.email);
      formData.append('mobileNum', this.candidate.mobileNum);
      formData.append('experience', this.candidate.experience);
      formData.append('jobId', this.candidate.jobId);
      formData.append('referredById', this.candidate.referredById);

      // Append file
      if (this.file) {
        formData.append('file', this.file);
      }

      try {
        const response = await api.post('/employee/referNewCandidate', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        });

        console.log('Response:', response.data);
        
        const toast = useToast();
        toast.success('Candidate referred successfully!');
        this.$router.push('/emp/joblist');

      } catch (error) {
        console.error('Error referring candidate:', error);
      }
    }
  }
};
</script>

<style scoped>
.registration-form {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #f9f9f9;
}

.form-group {
  margin-bottom: 1rem;
}

label {
  display: block;
  font-weight: bold;
}

input[type='text'],
input[type='email'],
input[type='number'],
input[type='tel'],
input[type='file'] {
  width: 100%;
  padding: 0.5rem;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 5px;
}

input[readonly] {
  background-color: #e9ecef; /* Light gray background for readonly fields */
  cursor: not-allowed; /* Show a not-allowed cursor */
}

.button-container {
  display: flex;
  justify-content: center; /* Center button horizontally */
  margin-top: 1rem; /* Space above the button */
}

button[type='submit'] {
  padding: 0.5rem 1rem;
  font-size: 1rem;
  color: white;
  background-color: #343a40; /* Dark button */
  border: none;
  cursor: pointer;
  border-radius: 5px;
}

button[type='submit']:hover {
  background-color: #23272b; /* Darker on hover */
}
</style>
