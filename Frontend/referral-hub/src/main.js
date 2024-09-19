import { createApp } from 'vue'
import App from './App.vue'
import { createRouter, createWebHistory} from 'vue-router'
import Toast from 'vue-toastification';
import 'vue-toastification/dist/index.css';
import SignIn from './components/nav/SignIn.vue'
import TheRegister from './components/nav/TheRegister.vue';
import EmployeeList from './components/admin/EmployeeList.vue'
import PendingApprovalList from './components/admin/PendingApprovalList.vue';
import CandidateRegistration from './components/employee/CandidateRegistration.vue';
import JobList from './components/employee/JobList.vue';
import ShowJobs from './components/hr/ShowJobs.vue';
import RegisterJob from './components/hr/RegisterJob.vue';
import ToJob from './components/hr/ToJob.vue';
import ShowJobDetails from './components/employee/ShowJobDetails.vue';
import ViewJobDetails from './components/hr/ViewJobDetails.vue';
import CandidateList from './components/hr/CandidateList.vue';
import store from './store';
import ReferredCandidates from './components/employee/ReferredCandidates.vue';
import ClosedJobs from './components/hr/ClosedJobs.vue';

const routes = [
            {   
                path: '/',
                component: SignIn
            },  
            {   
                path: '/signin',
                component: SignIn
            },
            {
                path: '/register',
                component: TheRegister
            },
            {
                path: '/emps/approved',
                component: EmployeeList,
                meta: { requireAuth: true, roles: ['ADMIN'] }
            },
            {
                path: '/emps/pending',
                component: PendingApprovalList,
                meta: { requireAuth: true, roles: ['ADMIN'] }
            },
            {
                path: '/emp/joblist',
                component: JobList,
                meta: { requireAuth: true, roles: ['EMPLOYEE'] }
            },
            {
              path:'/emp/referredcandidates/:id',
              component: ReferredCandidates,
              meta: { requireAuth: true, roles: ['EMPLOYEE'] }
          },
            {
                path: '/emp/joblist/register',
                component: CandidateRegistration,
                meta: { requireAuth: true, roles: ['EMPLOYEE'] }
            },//adding refer for hr too
            {
                path: '/emp/viewdetails/:id',
                component: ShowJobDetails,
                meta: { requireAuth: true, roles: ['EMPLOYEE'] }
            },
            {
                path: '/emp/joblist/:id',
                component: ViewJobDetails,
                meta: { requireAuth: true, roles: ['EMPLOYEE'] }
            },
            {
                path: '/job/:id',
                component: ToJob,
                meta: { requireAuth: true, roles: ['HR'] }
            },
            {
                path: '/jobs',
                component: ShowJobs,
                meta: { requireAuth: true, roles: ['HR'] }
            },
            {
                path: '/jobs/register',
                component: RegisterJob,
                meta: { requireAuth: true, roles: ['HR'] }
            },
            
            {
                path: '/emp/joblist/:id/candidates',
                component: CandidateList,
                meta: { requireAuth: true, roles: ['HR'] }
            },
            {
                path: '/emp/joblist/:id',
                component: ViewJobDetails,
                meta: { requireAuth: true, roles: ['HR'] }
            },
            {
              path: '/jobs/closed',
              component: ClosedJobs,
              meta: { requireAuth: true, roles: ['HR'] }
            },
        ]
    

    
        const router = createRouter({
            history: createWebHistory(),
            routes,
        });
        
        router.beforeEach((to, from, next) => {
            if (to.matched.some(record => record.meta.requiresAuth)) {
              if (!store.state.token) {
                next({ path: '/signin' });
              } else {
                const roles = store.state.roles;
                console.log("roles-->",roles);
                if (to.matched.some(record => record.meta.roles)) {
                  const hasRole = to.matched.some(record =>
                    record.meta.roles.some(role => roles.includes(role))
                  );
                  if (hasRole) {
                    next();
                  } else {
                    next({ path: '/' });
                  }
                } else {
                  next();
                }
              }
            } else {
              next();
            }
          });
        
        const app = createApp(App);
        
        
        app.config.globalProperties.$store = store;
        app.use(Toast, {
          // Optional configuration options
          position: 'top-right',      // Position of the toast
          timeout: 2000,              // Duration of the toast
          closeButton: true,          // Whether to show a close button
          pauseOnHover: true,         // Pause the toast on hover
          draggable: true             // Allow dragging
        });
        app.use(router);
        
        app.mount('#app');
