<template>
  <the-nav></the-nav>
  <router-view></router-view>
  <the-footer></the-footer>
</template>

<script>
import TheFooter from './components/nav/TheFooter.vue';
import TheNav from './components/admin/TheNav.vue';

export default {
  name: 'App',
  components: {
    TheNav,
    TheFooter,
  },
  data()
  {
    return {
      employees: [
        { id: 'e1', fullName: 'Twinkle Nemade', role: 'Back End Engineer', image : '<img src="../public/images.png" alt="NotF">', dept:'HR'},
        { id: 'e2', fullName: 'Pruthviraj Jadhav', role: 'Front End Engineer', image: '<img src="../public/images.png" alt="NotF">', dept:'Finance'},
        { id: 'e3', fullName: 'Ramdas Nalawade', role: 'Data Engineer', image: '<img src="../public/images.png" alt="NotF">', dept:'IT'}
      ],
    }
  },
  provide() {
    return {
      employees: this.employees,
    };
  }
}
</script>

<style src="../node_modules/bootstrap/dist/css/bootstrap.min.css">

</style>
