console.log("Application is running");

// const toggleSidebar = () => {
//     const $sidebar = $(".sidebar");
//     const $content = $(".content");

//     if ($sidebar.is(":visible")) {
//         $sidebar.hide(); // Use jQuery's hide() method
//         $content.css("margin-left", "1%"); // Adjust margin
//     } else {
//         $sidebar.show(); // Use jQuery's show() method
//         $content.css("margin-left", "20%"); // Adjust margin
//     }
// };

// with Animation
const toggleSidebar = () => {
    const $sidebar = $(".sidebar");
    const $content = $(".content");

    $sidebar.slideToggle(300, () => { // Slide toggle with 300ms animation
        if ($sidebar.is(":visible")) {
            $content.css("margin-left", "20%"); // Adjust margin
        } else {
            $content.css("margin-left", "1%"); // Reset margin
        }
    });
};



