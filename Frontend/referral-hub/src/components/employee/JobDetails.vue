<template>
  <li class="job-card">
    <h3 class="job-title">{{ job.title }}</h3>
    <p class="job-role">Role: {{ job.title }}</p>
    <p class="job-role">Job Id: {{ job.id }}</p>
    <p class="job-openings">Number of Openings: {{ job.numOpenings }}</p>
    <button type="button" class="btn btn-dark" @click="referCandidate">Refer a candidate</button> &nbsp;
    <button type="button" class="btn btn-dark" @click="getDetails(job.id)">View Details</button>
  </li>
</template>

<script>
import store from '@/store';

export default {
  props: { 
    job: {
      type: Object,
      required: true,
    }
  },
  data()
  {
    return {
      EmpId: store.state.EmpId,
    }
  },
  methods: {
    referCandidate() {
      console.log(this.EmpId);
      
      this.$router.push({ path: '/emp/joblist/register', query: {  EmpId: this.EmpId, jobId: this.job.id  } });
    },
    async getDetails(jobId) {
      this.$router.push(`/emp/viewdetails/${jobId}`);
    }
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
