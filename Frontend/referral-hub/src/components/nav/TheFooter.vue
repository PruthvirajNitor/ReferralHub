<template>
    <footer class="footer">
      <div class="container">
        <div class="row">
          <div class="col-md-4">
            <h2></h2>
          </div>
          <div class="col-md-4">
            <h5>Explore</h5>
            <ul>
              <li><a href="#">About Us</a></li>
              <li><a href="#">Contact Us</a></li>
            </ul>
          </div>
          <div class="col-md-4">
            <h5>Connect</h5>
            <ul class="list-unstyled">
              <li><a href="#">Facebook</a></li>
              <li><a href="#">Twitter</a></li>
            </ul>
          </div>
        </div>
        <hr>
        <div class="row">
          <div class="col-md-12">
            <p class="text-center">© 2024 ReferralHub. All Rights Reserved.</p>
          </div>
        </div>
      </div>
    </footer>
  </template>
  
  <script>
  export default {
  };
  </script>
  
  <style scoped>
  
  .footer {
    background-color:  black;
    padding: 40px 0;
    color: white;
  }
  
  .footer h5 {
    color: grey;
  }
  
  .footer ul {
    padding: 0;
  }
  
  .footer ul li {
    list-style-type: none;
    margin-bottom: 10px;
  }
  
  .footer ul li a {
    color: white;
    text-decoration: none;
  }
  
  .footer ul li a:hover {
    color: #333;
  }
  
  .footer hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border-color: #ddd;
  }
  
  .footer p {
    margin-bottom: 0;
  }
  </style>
  