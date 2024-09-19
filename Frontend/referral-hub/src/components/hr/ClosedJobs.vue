<template>
    <div>
      <section>
        <ul class="job-list">
          <li class="job-card" v-for="job in closedJobs" :key="job.id" role="button" tabindex="0">
            <h3 class="job-title">{{ job.title }}</h3>
            <p class="job-role">Job Id: {{ job.id }}</p>
            <p class="job-openings">Number of Openings: {{ job.numOpenings }}</p>
            <button type="button" class="btn btn-dark" @click="getDetails(job.id)">View Details</button> &nbsp;
            <button type="button" class="btn btn-dark" @click="getCandidates(job.id)">Referred Candidates</button>
          </li>
        </ul>
      </section>
    </div>
  </template>
  
  <script>
  import api from '@/utils/Axios'; // Ensure this path is correct
  
  export default {
    data() {
      return {
        closedJobs: [] // Array to hold closed jobs
      };
    },
    async created() {
      await this.fetchClosedJobs();
    },
    methods: {
      async fetchClosedJobs() {
        try {
          const response = await api.get('/hr/getClosedJobs');
          this.closedJobs = response.data;
        } catch (error) {
          console.error('Error fetching closed jobs:', error);
          // Optionally show an alert or error message to the user
        }
      },
      getDetails(jobId) {
        this.$router.push(`/emp/joblist/${jobId}`);
      },
      getCandidates(jobId) {
        this.$router.push(`/emp/joblist/${jobId}/candidates`);
      }
    }
  };
  </script>
  
  <style scoped>
  section {
    margin: 2rem auto;
    max-width: 1200px; /* Adjust based on desired width */
    padding: 1rem;
  }
  
  .job-list {
    display: flex;
    flex-wrap: wrap;
    gap: 1rem; /* Space between cards */
    list-style: none;
    padding: 0;
    margin: 0;
  }
  
  .job-card {
    background: #ffffff;
    border: 1px solid #ddd;
    border-radius: 12px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    padding: 1rem;
    transition: box-shadow 0.3s ease;
    box-sizing: border-box; /* Ensure padding and border are included in the width */
    cursor: pointer; /* Add cursor style to match job-details */
    flex: 1 1 calc(33.333% - 1rem); /* Make sure three cards fit in one row */
    max-width: calc(33.333% - 1rem); /* Ensure cards do not exceed their allocated width */
  }
  
  /* Responsive card widths */
  @media (max-width: 1199px) and (min-width: 768px) {
    .job-card {
      flex: 1 1 calc(50% - 1rem); /* Two cards per row on medium screens */
      max-width: calc(50% - 1rem); /* Ensure cards do not exceed their allocated width */
    }
  }
  
  @media (max-width: 767px) {
    .job-card {
      flex: 1 1 calc(100% - 1rem); /* One card per row on small screens */
      max-width: calc(100% - 1rem); /* Ensure cards do not exceed their allocated width */
    }
  }
  
  .job-card:hover {
    box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
  }
  
  .job-title {
    margin: 0 0 0.5rem 0;
    font-size: 1.25rem;
    color: #333;
  }
  
  .job-role, .job-openings {
    margin: 0;
    color: #666;
  }
  
  .btn {
    display: inline-block;
    padding: 0.5rem 1rem;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
  
  .btn-dark {
    background-color: #333;
    color: #fff;
  }
  </style>
  