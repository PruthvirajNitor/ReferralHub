<template>
    <div>
      <h1>List of Referred Candidates</h1>
      <table v-if="candidates.length">
        <thead>
          <tr>
            <th>Name</th>
            <th>Status</th>
            <th>Applied Job</th>
          </tr>
        </thead> 
        <tbody>
          <template v-for="candidate in candidates" :key="candidate.id">
            <tr v-for="(job, index) in candidate.appliedjob || []" :key="candidate.id + '-' + index">
              <td>{{ index === 0 ? candidate.name : '' }}</td>
              <td>{{ index === 0 ? candidate.status : '' }}</td>
              <td>{{ job.title }}</td>
            </tr>
          </template>
        </tbody>
      </table>
      <p v-else>No referred candidates found.</p>
    </div>
  </template>
 
  <script>
  import api from '@/utils/Axios';
  import store from '@/store';
 
  export default {
    data() {
      return {
        candidates: [],
        EmpId: store.state.EmpId, // Ensure you're using the correct key from the store
      };
    },
    created() {
      this.fetchReferredCandidates();
    },
    methods: {
      async fetchReferredCandidates() {
        if (this.EmpId) {
          try {
            const response = await api.get(`/employee/getReferredCandidates/${this.EmpId}`);
            if (response.data && Array.isArray(response.data)) {
              this.candidates = response.data.map(candidate => ({
                id: candidate.id,
                name: candidate.name,
                status: candidate.status,
                appliedjob: (candidate.appliedJob || []).map(job => ({ title: job.title })),
              }));
            } else {
              console.error('Invalid response format:', response.data);
            }
          } catch (error) {
            console.error('Error fetching referred candidates:', error);
          }
        } else {
          console.error('Employee ID is not set.');
        }
      },
    },
  };
  </script>
 
  <style scoped>
  .table-container {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
  }
 
  table {
    width: 100%;
    border-collapse: collapse;
  }
 
  th, td {
    border: 1px solid #ddd;
    padding: 8px;
  }
 
  th {
    background-color: #f4f4f4;
  }
  </style>