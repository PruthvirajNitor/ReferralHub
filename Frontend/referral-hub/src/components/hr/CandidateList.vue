<template>
  <div class="table-container">
    <h1>Referred Candidates</h1>
    <table v-if="candidates.length">
      <thead>
        <tr>
          <th>Name</th>
          <th>Status</th>
          <th>Actions</th>
          <th>Resume</th> <!-- New column for resume -->
        </tr>
      </thead>
      <tbody>
        <tr v-for="candidate in candidates" :key="candidate.id">
          <td>{{ candidate.name }}</td>
          <td>
            <select 
              v-model="candidate.editableStatus" 
              :disabled="candidate.updating"
            >
              <option v-for="status in statuses" :key="status" :value="status">
                {{ status }}
              </option>
            </select>
            <span v-if="candidate.updating">Updating...</span>
          </td>
          <td>
            <button 
              type="submit" 
              class="btn btn-dark" 
              @click="submitStatus(candidate)" 
              :disabled="candidate.updating"
            >
              Submit
            </button>
          </td>
          <td>
            <button 
              type="button" 
              class="btn btn-dark" 
              v-if="candidate.id"
              @click="downloadResume(candidate.id)"  
              :disabled="candidate.updating"
            >
              Download
            </button>
          </td>
        </tr>
      </tbody>
    </table>
    <p v-else>No candidates found.</p>
  </div>
</template>

<script>
import api from '@/utils/Axios';
import { useToast } from 'vue-toastification';

export default {
  data() {
    return {
      jobId: this.$route.params.id,
      candidates: [],
      statuses: ['APPLIED', 'SHORTLISTED', 'INTERVIEWED', 'REJECTED', 'HIRED']
    };
  },
  mounted() {
    this.fetchCandidates();
  },
  methods: {
    async fetchCandidates() {
      try {
        const response = await api.get(`/hr/getReferredCandidates/${this.jobId}`);
        this.candidates = response.data.map(candidate => ({
          id: candidate.id,
          name: candidate.name,
          status: candidate.status,
          editableStatus: candidate.status, 
          resume: candidate.resume, // Resume URL or path
          updating: false
        }));
      } catch (error) {
        console.error('Error fetching candidates', error);
      }
    },
    async submitStatus(candidate) {
      if (candidate.status !== candidate.editableStatus) {
        candidate.updating = true;
        try {
          const response = await api.put(
            `/hr/changeCandidateStatus/${candidate.id}/${this.jobId}`,
            candidate.editableStatus
          );
          if (response.status === 200) {
            // Update the candidate status with the new data from the backend
            const updatedCandidate = response.data;
            candidate.status = updatedCandidate.status; 
            candidate.editableStatus = updatedCandidate.status; 
          } else {
            console.error('Failed to update candidate status');
          }
          const toast = useToast();
        const notify = () => {
        toast.success('Status Updated !');
        };

notify();
        } catch (error) {
          console.error('Error updating candidate status', error);
        } finally {
          candidate.updating = false;
        }
      } else {
        console.log('Status is unchanged');
      }
    },
    async downloadResume(candidateId) {
      try {
        const response = await api.get(`/hr/downloadCandidateResume/${candidateId}`, {
          responseType: 'blob'
        });

        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', `resume_${candidateId}.pdf`); // Change extension as needed
        document.body.appendChild(link);
        link.click();
      } catch (error) {
        console.error('Error downloading resume', error);
      }
    }
  }
}
</script>

<style>
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

select {
  width: 100%;
  padding: 4px;
}

button {
  padding: 4px 8px;
}
</style>
