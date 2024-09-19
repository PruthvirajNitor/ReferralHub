<template> <br>
  <div>
    <div class="input-container">
      <input type="text" v-model="search" placeholder="Search by first name">
    </div>
    <div class="cen">
      <div class="para">
        <ul>
          <li v-for="emp in filteredEmployees" :key="emp.id" class="main">
            <div class="photo">
              <img :src="`../../assets/${emp.firstName}.png`" id="img" alt="Employee Photo">
            </div>
            <div class="employee-details">
              <h3>Name: {{ emp.firstName }} {{ emp.lastName }}</h3>
              <p>Department: {{ emp.department }}</p>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue';
import api from '@/utils/Axios';

export default {
  setup() {
    const employees = ref([]);
    const search = ref('');

    const fetchEmployees = async () => {
      try {
        const response = await api.get('/admin/approvedEmps');
        employees.value = response.data;
      } catch (error) {
        console.error('Error fetching employees', error);
      }
    };

    const filteredEmployees = computed(() => {
      return employees.value.filter(emp =>
        emp.firstName.toLowerCase().includes(search.value.toLowerCase())
      );
    });

    onMounted(() => {
      fetchEmployees();
    });

    return {
      employees,
      search,
      filteredEmployees
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
  border-radius: 25px; /* Rounded corners for a modern look */
  width: 300px; /* Adjust the width as needed */
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
  justify-content: center;
  margin: 0 auto; /* Center content horizontally */
}

.para {
  margin-top: 10px; /* Reduced margin-top to decrease space */
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
}

#img {
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

.role {
  border-radius: 40px;
  background-color: #ccc;
  color: #252525;
  padding: 0.25rem 1rem;
  display: inline-block;
}

.role--engineer {
  background-color: black;
  color: white;
}

.role--consultant {
  background-color: #af003a;
  color: white;
}
</style>
