<template>
  <pending-approval-list v-for="emp in employees" :key="emp.id" :name="emp.fullName" :imagePath="emp.imagepath" :dept="emp.dept"></pending-approval-list>
</template>

<script>
import PendingApprovalList from './PendingApprovalList.vue'

export default {
  components:{
    PendingApprovalList,
        
    },
    inject: ['employees']
}
</script>

<style scoped>
li {
    margin: 1rem 0;
    border: 1px solid #ccc;
    padding: 1rem;
  }
  
  h3 {
    margin: 0.5rem 0;
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