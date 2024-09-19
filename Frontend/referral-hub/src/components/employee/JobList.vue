<template> <br>
  <div>
    <div class="input-container">
      <input 
        type="text" 
        v-model="search" 
        placeholder="Search by job name"
      />
    </div>
    <section>
      <ul class="job-list">
        <JobDetails
          v-for="job in filteredJobs"
          :key="job.id"
          :job="job"
        />
      </ul>
    </section>
  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue';
import api from '@/utils/Axios';
import JobDetails from './JobDetails.vue';

export default {
  components: {
    JobDetails
  },
  setup() {
    const jobs = ref([]);
    const search = ref('');

    const fetchJobs = async () => {
      try {
        const response = await api.get('/employee/job-list');
        jobs.value = response.data; // Update jobs with the fetched data
      } catch (error) {
        console.error('Error fetching jobs', error);
      }
    };

    const filteredJobs = computed(() => {
      return jobs.value.filter(job =>
        job.title.toLowerCase().includes(search.value.toLowerCase())
      );
    });

    onMounted(() => {
      fetchJobs(); // Fetch job data when the component is mounted
    });

    return {
      jobs,
      search,
      filteredJobs
    };
  }
}
</script>

<style scoped>
section {
  margin: 2rem auto;
  max-width: 1200px; /* Adjust based on desired width */
  padding: 1rem;
}

.input-container {
  display: flex;
  justify-content: center;
  margin-bottom: 20px; /* Space between input and cards */
  width: 100%;
}

input[type="text"] {
  padding: 12px 16px; /* Adjust padding for better appearance */
  font-size: 16px;
  border: 2px solid #ddd;
  border-radius: 25px; /* Rounded corners for a modern look */
  width: 300px; /* Adjust the width as needed */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  outline: none;
  transition: all 0.3s ease;
  background-color: #f9f9f9; /* Light background color */
}

input[type="text"]:focus {
  border-color: #007bff;
  box-shadow: 0 0 8px rgba(0, 123, 255, 0.5);
  background-color: #ffffff; /* White background on focus */
}

.job-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr)); /* Auto-fit cards with a minimum size */
  gap: 1rem; /* Same spacing between cards horizontally and vertically */
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
  box-sizing: border-box; /* Ensure padding and border are included in the width */
  transition: box-shadow 0.3s ease;
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
