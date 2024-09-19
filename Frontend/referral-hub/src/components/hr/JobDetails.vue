<template>
  <li class="job-card" role="button" tabindex="0">
    <h3 class="job-title">{{ job.title }}</h3>  
    <p class="job-role">Job Id: {{ job.id }}</p>
    <p class="job-openings">Number of Openings: {{ job.numOpenings }}</p>
    <button type="submit" class="btn btn-dark" @click="getDetails(job.id)" ref="">View Details</button> &nbsp;
    <button type="submit" class="btn btn-dark" @click="getCandidates(job.id)" ref="">Referred Candidates</button>
  </li>
</template>

<script>
import api from '@/utils/Axios';

export default {
  props: { 
    job: {
      type: Object,
      required: true,
    }   
  },
  methods: {
    // async toJob() {
    //   try {
    //     const response = await axios.get('http://localhost:9090/admin/approvedEmps');
    //     this.employees = response.data;
    //   } catch (error) {
    //     console.error('Error fetching employees', error);
    //   }    
    //   this.$router.push({ name: 'ToJob', params: { id: this.job.id } });
    // },
    async closeOpening(jobId)
    {
      try {
        await api.put(`/hr/${jobId}/status`, {
          
        });
      } catch (error) {
        console.error('Error removing job ', error);
      }
    },
    async getDetails(jobId)
    {
        this.$router.push(`/emp/joblist/${jobId}`);
      
    },
    async getCandidates(jobId)
    {
      this.$router.push(`/emp/joblist/${jobId}/candidates`)
    },
  }
}
</script>

<style scoped>
.job-card {
  background: #ffffff;
  border: 1px solid #ddd;
  border-radius: 12px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  margin-bottom: 1rem;
  padding: 1rem;
  transition: box-shadow 0.3s ease;
  cursor: pointer; 
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
</style>
