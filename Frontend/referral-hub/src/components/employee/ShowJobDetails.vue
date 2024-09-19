<template>
    <div class="card" v-if="jobDetails">
      <div class="card-header">
        <h1>{{ jobDetails.title }}</h1>
      </div>
      <div class="card-body">
        <p><strong>Description:</strong> {{ jobDetails.description }}</p>
        <p><strong>Salary:</strong> Rs {{ jobDetails.salary }}</p>
        <p><strong>Location:</strong> {{ jobDetails.location }}</p>
        <p><strong>Skills Required:</strong> {{ jobDetails.skillsRequired.join(', ') }}</p>
        <p><strong>Experience Required:</strong> {{ jobDetails.experienceRequired }} years</p>
        <p><strong>Number of Openings:</strong> {{ jobDetails.numOpenings }}</p>
      </div>
      <div class="card-footer">
        <button type="submit" class="btn btn-dark" @click="goBack" ref="">Back</button>
      </div>
    </div>
    <div v-else class="loading">
      <p>Loading job details...</p>
    </div>
  </template>
  
  <script>
  import api from '@/utils/Axios';
  
  export default {
    name: 'JobDetails',
    data() {
      return {
        jobDetails: null,
        jobId: this.$route.params.id // Assuming you're using Vue Router
      };
    },
    created() {
      this.fetchJobDetails();
    },
    methods: {
      async fetchJobDetails() {
        try {
          const response = await api.get(`/employee/${this.jobId}/getJobDetails`);
          this.jobDetails = response.data;
        } catch (error) {
          console.error('Error fetching job details:', error);
        }
      },
      goBack() {
        this.$router.go(-1);
      }
    }
  };
  </script>
  
  <style scoped>
  .card {
    max-width: 600px;
    margin: 20px auto;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    background-color: #ffffff;
  }
  
  .card-header {
    border-bottom: 2px solid #f0f0f0;
    padding-bottom: 10px;
    margin-bottom: 20px;
  }
  
  .card-header h1 {
    margin: 0;
    font-size: 24px;
    color: #333;
  }
  
  .card-body p {
    margin: 10px 0;
    font-size: 16px;
    color: #555;
  }
  
  .card-body strong {
    color: #333;
  }
  
  .card-footer {
    text-align: center;
    margin-top: 20px;
  }
  
  .card-footer button {
    padding: 10px 20px;
    border: none;
    border-radius: 4px;
    background-color: black;
    color: #fff;
    font-size: 16px;
    cursor: pointer;
    transition: background-color 0.3s ease;
  }
  
  
  .loading {
    text-align: center;
    font-size: 18px;
    color: #888;
  }
  </style>
  