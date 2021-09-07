<template>
    <animated-wallpaper :bg-img="bgImg">
        <a-menu selectedKeys="mail" mode="horizontal">
            <a-menu-item key="mail">
                <template #icon>
                    <mail-outlined />
                </template>
                Navigation One
            </a-menu-item>
        </a-menu>
    </animated-wallpaper>
</template>

<script>
    import { MailOutlined, AppstoreOutlined, SettingOutlined } from '@ant-design/icons-vue';
    import AnimatedWallpaper from "@/components/animated-wallpaper/index.vue";
    import {Image} from "@/common/entity/Image";

    export default {
        name: "test",
        components: {
            AnimatedWallpaper,
            MailOutlined,
            AppstoreOutlined,
            SettingOutlined,
        },
        data() {
            return {
                bgImg: Image.LGOIN
            }
        },
    }
</script>

<style scoped>

</style>