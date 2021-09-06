import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router';

const routes: Array<RouteRecordRaw> = [
    {
        path: '/chat',
        name: 'chat',
        component: () => import('@/views/auth-app/chat/index.vue'),
    },
    {
        path: '/test',
        name: 'test',
        component: () => import('@/views/home-page/test/index.vue'),
        meta: {
            title: "测试页面"
        }
    },
    {
        path: '/',
        redirect: {
            name: "test"
        }
    }
];

const router = createRouter({
    history: createWebHashHistory(),
    routes,
});

export default router;
