<template> <br>
  <div>
    <div class="input-container">
      <input type="text" v-model="search" placeholder="Search by job name">
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
import api from '@/utils/Axios';
import JobDetails from '../hr/JobDetails.vue';
import { ref, computed, onMounted } from 'vue';

export default {
  components: {
    JobDetails // Register the JobDetails component
  },
  setup() {
    const jobs = ref([]);
    const search = ref('');

    const fetchJobs = async () => {
      try {
        const response = await api.get('/hr/job-list');
        jobs.value = response.data; // Assign the job data to the component's state
      } catch (error) {
        console.error('Error fetching jobs', error); // Log any errors
      }
    };

    const filteredJobs = computed(() => {
      return jobs.value.filter(job =>
        job.title.toLowerCase().includes(search.value.toLowerCase())
      );
    });

    onMounted(() => {
      fetchJobs();
    });

    const postJob = () => {
      this.$router.push('/jobs/register');
    };

    return {
      search,
      filteredJobs,
      postJob
    };
  }
}
</script>

<style scoped>
.btn-container {
  display: flex;
  justify-content: center;
  align-items: center;
}

.btn {
  padding: 0.5rem 1rem;
}

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
}

/* Default: 3 cards per row with space between */
@media (min-width: 1200px) {
  .job-card {
    width: calc(33.333% - 1rem);
  }
}

/* Medium screens: 2 cards per row */
@media (max-width: 1199px) and (min-width: 768px) {
  .job-card {
    width: calc(50% - 1rem);
  }
}

/* Small screens: 1 card per row */
@media (max-width: 767px) {
  .job-card {
    width: calc(100% - 1rem);
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
