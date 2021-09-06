<template>
    <animated-wallpaper :bg-img="bgImg" />
</template>

<script>
    import AnimatedWallpaper from "@/components/animated-wallpaper/index.vue";
    import {Image} from "@/common/entity/Image";

    export default {
        name: "test",
        components: {
            AnimatedWallpaper
        },
        data() {
            return {
                bgImg: Image.LGOIN
            }
        }
    }
</script>

<style scoped>

</style>