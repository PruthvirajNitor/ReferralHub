<template> <br>
  <div class="container">
    <div class="input-container">
      <input type="text" v-model="search" placeholder="Search by first name">
    </div>
    <div class="cen">
      <div class="para">
        <ul>
          <li v-for="emp in filteredEmployees" :key="emp.id" class="main">
            <div class="photo">
              <img :src="`../../assets/${emp.firstName}.png`" alt="Employee Photo" />
            </div>
            <div class="employee-details">
              <h3>Name: {{ emp.firstName }} {{ emp.lastName }}</h3>
              <p>Department: {{ emp.department }}</p>
              <p>Id: {{ emp.id }}</p>
              <p>Status: {{ emp.status ? 'Approved' : 'Pending' }}</p>
              <button v-if="!emp.status" type="button" class="btn btn-success" @click="updateStatus(emp.id)">Approve</button> &nbsp;
              <button v-if="!emp.status" type="button" class="btn btn-danger" @click="deleteEmp(emp.id)">Decline</button>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, computed } from 'vue';
import api from '@/utils/Axios';
import { useToast } from 'vue-toastification';

export default {
  setup() {
    const employees = ref([]);
    const search = ref('');

    const fetchEmployees = async () => {
      try {
        const response = await api.get('/admin/pendingEmps');
        employees.value = response.data;
      } catch (error) {
        console.error('Error fetching employees', error);
      }
    };

    const filteredEmployees = computed(() => {
      const searchLower = search.value.toLowerCase();
      return employees.value.filter(emp =>
        emp.firstName.toLowerCase().includes(searchLower)
      );
    });

    const updateStatus = async (empId) => {
      try {
        await api.put(`/admin/${empId}/status`, { status: true });
        
        const toast = useToast();
        const notify = () => {
        toast.success('Employee Approved !');
        };

        notify();

        fetchEmployees(); // Refresh employee data
      } catch (error) {
        console.error('Error updating employee status', error);
      }
    };

    const deleteEmp = async (empId) => {
      try {
        await api.delete(`/admin/deleteEmp/${empId}`);
        
        const toast = useToast();
        const notify = () => {
        toast.success('Employee Declined !');
        };

        notify();

        fetchEmployees(); // Refresh employee data
      } catch (error) {
        console.error('Error deleting employee', error);
        alert('Error deleting employee. Please try again.');
      }
    };

    onMounted(() => {
      fetchEmployees();
    });

    return {
      employees,
      search,
      filteredEmployees,
      updateStatus,
      deleteEmp
    };
  }
};
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
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
  border-radius: 30px; /* Rounded corners for a modern look */
  width: 300px; /* Adjust the width as needed */
  background-color: #f9f9f9; /* Light background color */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  outline: none;
  transition: all 0.3s ease;
}

input[type="text"]:focus {
  border-color: #007bff;
  box-shadow: 0 0 8px rgba(0, 123, 255, 0.5);
}

.cen {
  display: flex;
  flex-wrap: wrap; /* Allows items to wrap to the next line */
  justify-content: center; /* Center the cards horizontally */
}

.para {
  width: 100%;
  display: flex;
  flex-wrap: wrap; /* Allows list items to wrap to the next line */
  justify-content: center; /* Center the list items horizontally */
  padding: 0;
  margin: 0;
}

.main {
  display: flex;
  align-items: center;
  width: 500px; /* Adjust width as needed */
  margin: 10px; /* Space between cards */
  box-shadow: 3px 4px 10px rgb(185, 185, 171);
  border-radius: 12px; /* Add border radius to the card */
  overflow: hidden; /* Ensure child elements are clipped within the rounded corners */
  transform: translateX(-10px); /* Shift each card slightly to the left */
}

.photo {
  margin: 10px;
  border: 1px solid black;
  height: 147px;
  width: 122px;
  flex-shrink: 0; /* Prevents image from stretching */
  border-radius: 12px; /* Add border radius to the photo container */
}

.employee-details {
  padding: 10px;
  border-radius: 12px; /* Add border radius to the details section */
}

img {
  object-fit: cover;
  height: 100%;
  width: 100%;
  border-radius: 50%; /* Makes the image circular */
}

li {
  list-style-type: none; /* Removes default bullet point */
  border: 1px solid #ccc;
  padding: 1rem;
  margin: 1rem 0;
  border-radius: 12px; /* Add border radius to the list item */
}

h3 {
  margin: 0.5rem 0;
}

p {
  margin: 0;
}

.btn {
  padding: 5px 10px;
  border: none;
  border-radius: 4px;
  color: white;
  cursor: pointer;
}

.btn-success {
  background-color: #28a745;
}

.btn-danger {
  background-color: #dc3545;
}
</style>
