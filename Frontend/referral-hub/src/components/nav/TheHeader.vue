<template>
  <header>
    <nav>
      <ul>
        <li class="logo">
          <router-link to="/">
            <h1>ReferralHub</h1>
          </router-link>
        </li>
        <li class="menu">
          <router-link to="/register">Register</router-link>
        </li>
        <li class="menu">
          <router-link to="/emps/pending">Requests</router-link>
        </li>
      </ul>
    </nav>
  </header>
</template>

<script>
export default {
  methods: {}
}
</script>

<style scoped>
header {
  width: 100%;
  background-color: black;
}

nav {
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

ul {
  list-style: none;
  margin: 0;
  padding: 0;
  display: flex;
  gap: 2rem; /* Space between items */
  align-items: center;
}

.logo h1 {
  margin: 0;
  color: white;
  font-size: 1.5rem; /* Adjust font size for better visibility */
}

.menu {
  margin: 0;
}

a {
  text-decoration: none;
  color: white;
  padding: 0.5rem 1rem;
  transition: background-color 0.3s, color 0.3s;
}

a:hover,
a:active,
a.router-link-active {
  background-color: grey;
  color: white;
}

/* Responsive styles */
@media (max-width: 768px) {
  ul {
    flex-direction: column; /* Stack items vertically on smaller screens */
    align-items: flex-start; /* Align items to the start */
    gap: 1rem; /* Reduce space between items */
  }

  .logo h1 {
    font-size: 1.25rem; /* Slightly smaller font size on smaller screens */
  }

  a {
    padding: 0.5rem;
    font-size: 0.875rem; /* Adjust font size on smaller screens */
  }
}

@media (max-width: 480px) {
  header {
    padding: 0.5rem; /* Add padding to header */
  }

  ul {
    width: 100%; /* Full width for mobile screens */
    padding: 0;
  }

  a {
    padding: 0.5rem 1rem;
    font-size: 0.75rem; /* Smaller font size for very small screens */
  }
}
</style>
