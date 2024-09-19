<template>
  <div class="registration-form">
    <h2 class="form-title">Job Details</h2>
    <form @submit.prevent="postJob">
      <div class="form-group">
        <label for="title">Title</label>
        <input type="text" id="title" v-model="job.title" required autofocus />
      </div>
      <div class="form-group">
        <label for="description">Description</label>
        <input type="text" id="description" v-model="job.description" required />
      </div>
      <div class="form-group">
        <label for="salary">Salary</label>
        <input type="number" id="salary" v-model="job.salary" required />
      </div>
      <div class="form-group">
        <label for="location">Location</label>
        <input type="text" id="location" v-model="job.location" required />
      </div>
      <div class="form-group">
        <label for="skillsRequired">Skills Required</label>
        <input type="text" id="skillsRequired" v-model="job.skillsRequired" required />
      </div>
      <div class="form-group">
        <label for="experienceRequired">Experience Required</label>
        <input type="number" id="experienceRequired" v-model="job.experienceRequired" required />
      </div>
      <div class="form-group">
        <label for="numOpenings">Number of Openings</label>
        <input type="number" id="numOpenings" v-model="job.numOpenings" required />
      </div>
      <div class="button-container">
        <button type="submit" class="btn">Register</button>
      </div>
    </form>
  </div>
</template>

<script>
import api from '@/utils/Axios';
import { useToast } from 'vue-toastification';

export default {
  data() {
    return {
      job: {
        title: '',
        description: '',
        salary: null,
        location: '',
        skillsRequired: '',
        experienceRequired: null,
        numOpenings: null,
      }
    }
  },
  methods: {
    async postJob() {
      const formData = new FormData();
      
      // Append form data
      formData.append('title', this.job.title);
      formData.append('description', this.job.description);
      formData.append('salary', this.job.salary);
      formData.append('location', this.job.location);
      formData.append('skillsRequired', this.job.skillsRequired);
      formData.append('experienceRequired', this.job.experienceRequired);
      formData.append('numOpenings', this.job.numOpenings);

      try {
        const response = await api.post('/hr/addJob', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        });
        console.log('Response:', response.data);

        const toast = useToast();
        const notify = () => {
        toast.success('Job registered successfully !');
        };

        notify();
        this.$router.push('/jobs');
      } catch (error) {
        console.error('Error posting job:', error);
        alert('Failed to post job. Please try again.');
      }
    }
  }
}
</script>

<style scoped>
.registration-form {
  max-width: 600px; /* Increased the width of the form */
  margin: 0 auto;
  padding: 40px;
  border-radius: 10px;
  background: linear-gradient(135deg, #f3f4f6, #e5e5e5);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
}

.form-title {
  font-size: 2rem;
  margin-bottom: 1.5rem;
  text-align: center;
  color: #000; /* Set title color to black */
}

.form-group {
  margin-bottom: 1.5rem;
}

label {
  display: block;
  font-weight: bold;
  margin-bottom: 0.5rem;
  color: #333;
}

input[type='text'],
input[type='number'] {
  width: 100%;
  padding: 0.75rem;
  font-size: 1rem;
  border: none; /* Remove border */
  border-radius: 8px;
  box-sizing: border-box;
  background: #ffffff;
  transition: box-shadow 0.3s;
}

input[type='text']:focus,
input[type='number']:focus {
  box-shadow: 0 0 8px rgba(0, 0, 0, 0.5); /* Add shadow for focus effect */
  outline: none;
}

.button-container {
  text-align: center;
  margin-top: 2rem;
}

button[type='submit'] {
  padding: 0.75rem 1.5rem;
  font-size: 1.125rem;
  color: #fff;
  background-color: #000; /* Set button color to black */
  border: none;
  cursor: pointer;
  border-radius: 8px;
  transition: background-color 0.3s, transform 0.3s;
}

button[type='submit']:hover {
  background-color: #333; /* Darker black for hover effect */
  transform: translateY(-2px);
}

button[type='submit']:active {
  transform: translateY(0);
}
</style>
