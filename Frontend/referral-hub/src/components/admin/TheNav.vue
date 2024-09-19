<template>
  <header>
    <div class="container">
      <nav>
        <ul>
          <li><img class="logo" src="../../../public/assets/Final logo-Photoroom.png" alt="Logo"></li>
          <li class="nav-item">
            <router-link to="/" style="text-decoration: none;" exact active-class="" exact-active-class="active-link">
              <h2 class="logo-text">ReferralHub</h2>
            </router-link>
          </li>
          <!-- <li class="nav-item">
            <div class="input-container">
              <input type="text" v-model="search" placeholder="Search by first name">
            </div>
          </li> -->
          <li v-if="isAdmin" class="nav-item">
            <router-link to="/emps/approved" class="nav-link" active-class="active-link"><strong>Approved Employees</strong></router-link>
          </li>
          <li v-if="isAdmin" class="nav-item">
            <router-link to="/emps/pending" class="nav-link" active-class="active-link"><strong>Pending Approvals</strong></router-link>
          </li>
          <li v-if="isEmployee" class="nav-item">
            <router-link to="/emp/joblist" class="nav-link" active-class="active-link"><strong>Job Openings</strong></router-link>
          </li>
          <li v-if="isEmployee" class="nav-item">
            <router-link :to="`/emp/referredcandidates/${userId}`" class="nav-link" active-class="active-link"><strong>Referred Candidates</strong></router-link>
          </li>
          <li v-if="isHr" class="nav-item">
            <router-link to="/jobs" class="nav-link" active-class="active-link"><strong>Job List</strong></router-link>
          </li>
          <li v-if="isHr" class="nav-item">
            <router-link to="/jobs/register" class="nav-link" active-class="active-link" @click="postJob"><strong>Post Job</strong></router-link>
          </li>
          <li v-if="isHr" class="nav-item">
            <router-link to="/jobs/closed" class="nav-link" active-class="active-link" @click="closedJobs"><strong>Closed Jobs</strong></router-link>
          </li>
          <li v-if="isAdmin || isEmployee || isHr" class="nav-item nav-item-right">
            <div class="profile-container" @click="toggleDropdown">
              <span class="profile-name">{{ name }}</span>
              <img class="profile-img" src="../../../public/assets/holder.jpg" alt="Profile Image">
              <div v-if="dropdownVisible" class="dropdown-menu">
                <router-link to="/" class="dropdown-item" @click="signOut">Sign Out</router-link>
              </div>
            </div>
          </li>
        </ul>
      </nav>
    </div>
  </header>
</template>

<script>
import { mapState } from 'vuex';

export default {
  data() {
    return {
      dropdownVisible: false
    };
  },
  computed: {
    ...mapState({
      name: state => state.EmpName,
      isAdmin: state => state.roles.includes('ROLE_ADMIN'),
      isEmployee: state => state.roles.includes('ROLE_EMPLOYEE'),
      isHr: state => state.roles.includes('ROLE_HR'),
      userId: state => state.userId
    })
  },
  methods: {
    postJob() {
      this.$router.push('/jobs/register');
    },
    closedJobs() {
      this.$router.push('/jobs/closed');
    },
    signOut() {
      localStorage.removeItem('token');
      this.$router.push('/').then(() => {
        window.location.reload();
      });
    },
    toggleDropdown() {
      this.dropdownVisible = !this.dropdownVisible;
    },
  },
};
</script>

<style scoped>
header {
  width: 100%;
  height: 4rem;
  background-color: black;
}

.logo {
  height: 3rem;
  width: 3rem;
  margin-top: 10px;
}

.logo-text {
  text-decoration: none;
  color: white;
  margin: 0;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
}

nav {
  height: 100%;
  display: flex;
  align-items: center; /* Center items vertically */
}

ul {
  list-style: none;
  margin: 0;
  padding: 0;
  display: flex;
  align-items: center; /* Align items vertically in the middle */
  width: 100%;
}

.nav-item {
  margin: 0 0.5rem; /* Space between items */
}

.nav-link {
  text-decoration: none;
  background: transparent;
  border: 1px solid transparent;
  cursor: pointer;
  color: white;
  padding: 0.5rem 1rem;
  display: flex;
  align-items: center; /* Align items in the center */
}

.profile-container {
  display: flex;
  align-items: center;
  position: relative;
  cursor: pointer;
  padding: 0.5rem 1rem; /* Add padding for spacing */
}

.profile-container:hover {
  background-color: grey; /* Highlight on hover */
}

.profile-name {
  color: white;
  margin-right: 0.5rem;
}

.profile-img {
  height: 2rem;
  width: 2rem;
  border-radius: 50%; /* Make the image circular */
  margin-left: 0.5rem; /* Space between the name and image */
}

.dropdown-menu {
  position: absolute;
  top: 100%;
  right: 0;
  background-color: white;
  border: 1px solid #ddd;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  border-radius: 4px;
  z-index: 1000;
  display: flex;
  flex-direction: column;
  width: 100px; /* Adjust width to fit the content */
}

.dropdown-item {
  text-decoration: none;
  color: black;
  padding: 0.5rem 1rem;
  white-space: nowrap;
}

.dropdown-item:hover {
  background-color: #f0f0f0;
}

.input-container {
  display: flex;
  align-items: center; /* Align input field vertically with other items */
  margin-left: auto; /* Pushes input field to the right */
}

input[type="text"] {
  padding: 10px 15px;
  font-size: 16px;
  border: 2px solid #ddd;
  border-radius: 20px;
  width: 250px;
  background-color: #f0f0f0;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  outline: none;
  transition: all 0.3s ease;
}

input[type="text"]:focus {
  border-color: #007bff;
  background-color: white;
  box-shadow: 0 0 8px rgba(0, 123, 255, 0.5);
}

.nav-link:hover {
  background-color: grey;
}

.active-link {
  background-color: grey;
  color: white;
}

#signout {
  margin-left: auto; /* Align Sign Out link to the far right */
}

/* New style to push the last item to the far right */
.nav-item-right {
  margin-left: auto; /* Pushes this item to the right */
}
</style>
