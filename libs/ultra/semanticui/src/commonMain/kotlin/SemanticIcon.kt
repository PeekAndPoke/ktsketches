package de.peekandpoke.ultra.semanticui

import kotlinx.html.FlowContent
import kotlinx.html.I
import kotlinx.html.i

@Suppress("PropertyName", "FunctionName", "unused")
class SemanticIcon(private val parent: FlowContent) {

    @Suppress("EnumEntryName", "unused")
    enum class Name(val cls: String) {

        // Accessibility ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

        american_sign_language_interpreting("american sign language interpreting"),
        assistive_listening_systems("assistive listening systems"),
        audio_description("audio description"),
        blind("blind"),
        braille("braille"),
        closed_captioning("closed captioning"),
        closed_captioning_outline("closed captioning outline"),
        deaf("deaf"),
        low_vision("low vision"),
        phone_volume("phone volume"),
        question_circle("question circle"),
        question_circle_outline("question circle outline"),
        sign_language("sign language"),
        tty("tty"),
        universal_access("universal access"),
        wheelchair("wheelchair"),

        // Arrows ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        angle_double_down("angle double down"),
        angle_double_left("angle double left"),
        angle_double_right("angle double right"),
        angle_double_up("angle double up"),
        angle_down("angle down"),
        angle_left("angle left"),
        angle_right("angle right"),
        angle_up("angle up"),
        arrow_alternate_circle_down("arrow alternate circle down"),
        arrow_alternate_circle_down_outline("arrow alternate circle down outline"),
        arrow_alternate_circle_left("arrow alternate circle left"),
        arrow_alternate_circle_left_outline("arrow alternate circle left outline"),
        arrow_alternate_circle_right("arrow alternate circle right"),
        arrow_alternate_circle_right_outline("arrow alternate circle right outline"),
        arrow_alternate_circle_up("arrow alternate circle up"),
        arrow_alternate_circle_up_outline("arrow alternate circle up outline"),
        arrow_circle_down("arrow circle down"),
        arrow_circle_left("arrow circle left"),
        arrow_circle_right("arrow circle right"),
        arrow_circle_up("arrow circle up"),
        arrow_down("arrow down"),
        arrow_left("arrow left"),
        arrow_right("arrow right"),
        arrow_up("arrow up"),
        arrows_alternate("arrows alternate"),
        arrows_alternate_horizontal("arrows alternate horizontal"),
        arrows_alternate_vertical("arrows alternate vertical"),
        caret_down("caret down"),
        caret_left("caret left"),
        caret_right("caret right"),
        caret_square_down("caret square down"),
        caret_square_down_outline("caret square down outline"),
        caret_square_left("caret square left"),
        caret_square_left_outline("caret square left outline"),
        caret_square_right("caret square right"),
        caret_square_right_outline("caret square right outline"),
        caret_square_up("caret square up"),
        caret_square_up_outline("caret square up outline"),
        caret_up("caret up"),
        cart_arrow_down("cart arrow down"),
        chart_line("chart line"),
        chevron_circle_down("chevron circle down"),
        chevron_circle_left("chevron circle left"),
        chevron_circle_right("chevron circle right"),
        chevron_circle_up("chevron circle up"),
        chevron_down("chevron down"),
        chevron_left("chevron left"),
        chevron_right("chevron right"),
        chevron_up("chevron up"),
        cloud_download("cloud download"),
        cloud_upload("cloud upload"),
        download("download"),
        exchange_alternate("exchange alternate"),
        expand_arrows_alternate("expand arrows alternate"),
        external_alternate("external alternate"),
        external_square_alternate("external square alternate"),
        hand_point_down("hand point down"),
        hand_point_down_outline("hand point down outline"),
        hand_point_left("hand point left"),
        hand_point_left_outline("hand point left outline"),
        hand_point_right("hand point right"),
        hand_point_right_outline("hand point right outline"),
        hand_point_up("hand point up"),
        hand_point_up_outline("hand point up outline"),
        hand_pointer("hand pointer"),
        hand_pointer_outline("hand pointer outline"),
        history("history"),
        level_down_alternate("level down alternate"),
        level_up_alternate("level up alternate"),
        location_arrow("location arrow"),
        long_arrow_alternate_down("long arrow alternate down"),
        long_arrow_alternate_left("long arrow alternate left"),
        long_arrow_alternate_right("long arrow alternate right"),
        long_arrow_alternate_up("long arrow alternate up"),
        mouse_pointer("mouse pointer"),
        play("play"),
        random("random"),
        recycle("recycle"),
        redo("redo"),
        redo_alternate("redo alternate"),
        reply("reply"),
        reply_all("reply all"),
        retweet("retweet"),
        share("share"),
        share_square("share square"),
        share_square_outline("share square outline"),
        sign_in_alternate("sign in alternate"),
        sign_out_alternate("sign out alternate"),
        sort("sort"),
        sort_alphabet_down("sort alphabet down"),
        sort_alphabet_up("sort alphabet up"),
        sort_amount_down("sort amount down"),
        sort_amount_up("sort amount up"),
        sort_down("sort down"),
        sort_numeric_down("sort numeric down"),
        sort_numeric_up("sort numeric up"),
        sort_up("sort up"),
        sync("sync"),
        sync_alternate("sync alternate"),
        text_height("text height"),
        text_width("text width"),
        undo("undo"),
        undo_alternate("undo alternate"),
        upload("upload"),

        // Audio and Video ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        backward("backward"),
        circle("circle"),
        circle_outline("circle outline"),
        compress("compress"),
        eject("eject"),
        expand("expand"),
        fast_backward("fast backward"),
        fast_forward("fast forward"),
        file_audio("file audio"),
        file_audio_outline("file audio outline"),
        file_video("file video"),
        file_video_outline("file video outline"),
        film("film"),
        forward("forward"),
        headphones("headphones"),
        microphone("microphone"),
        microphone_slash("microphone slash"),
        music("music"),
        pause("pause"),
        pause_circle("pause circle"),
        pause_circle_outline("pause circle outline"),
        play_circle("play circle"),
        play_circle_outline("play circle outline"),
        podcast("podcast"),
        rss("rss"),
        rss_square("rss square"),
        step_backward("step backward"),
        step_forward("step forward"),
        stop("stop"),
        stop_circle("stop circle"),
        stop_circle_outline("stop circle outline"),
        video("video"),
        volume_down("volume down"),
        volume_off("volume off"),
        volume_up("volume up"),

        // Business //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        address_book("address book"),
        address_book_outline("address book outline"),
        address_card("address card"),
        address_card_outline("address card outline"),
        archive("archive"),
        balance_scale("balance scale"),
        birthday_cake("birthday cake"),
        book("book"),
        briefcase("briefcase"),
        building("building"),
        building_outline("building outline"),
        bullhorn("bullhorn"),
        bullseye("bullseye"),
        calculator("calculator"),
        calendar("calendar"),
        calendar_outline("calendar outline"),
        calendar_alternate("calendar alternate"),
        calendar_alternate_outline("calendar alternate outline"),
        certificate("certificate"),
        chart_area("chart area"),
        chart_bar("chart bar"),
        chart_bar_outline("chart bar outline"),
        chart_pie("chart pie"),
        clipboard("clipboard"),
        clipboard_outline("clipboard outline"),
        coffee("coffee"),
        columns("columns"),
        compass("compass"),
        compass_outline("compass outline"),
        copy("copy"),
        copy_outline("copy outline"),
        copyright("copyright"),
        copyright_outline("copyright outline"),
        cut("cut"),
        edit("edit"),
        edit_outline("edit outline"),
        envelope("envelope"),
        envelope_outline("envelope outline"),
        envelope_open("envelope open"),
        envelope_open_outline("envelope open outline"),
        envelope_square("envelope square"),
        eraser("eraser"),
        fax("fax"),
        file("file"),
        file_outline("file outline"),
        file_alternate("file alternate"),
        file_alternate_outline("file alternate outline"),
        folder("folder"),
        folder_outline("folder outline"),
        folder_open("folder open"),
        folder_open_outline("folder open outline"),
        globe("globe"),
        industry("industry"),
        paperclip("paperclip"),
        paste("paste"),
        pen_square("pen square"),
        pencil_alternate("pencil alternate"),
        percent("percent"),
        phone("phone"),
        phone_square("phone square"),
        registered("registered"),
        registered_outline("registered outline"),
        save("save"),
        save_outline("save outline"),
        sitemap("sitemap"),
        sticky_note("sticky note"),
        sticky_note_outline("sticky note outline"),
        suitcase("suitcase"),
        table("table"),
        tag("tag"),
        tags("tags"),
        tasks("tasks"),
        thumbtack("thumbtack"),
        trademark("trademark"),

        // Code ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        barcode("barcode"),
        bath("bath"),
        bug("bug"),
        code("code"),
        code_branch("code branch"),
        file_code("file code"),
        file_code_outline("file code outline"),
        filter("filter"),
        fire_extinguisher("fire extinguisher"),
        fork("fork"),
        qrcode("qrcode"),
        shield_alternate("shield alternate"),
        terminal("terminal"),
        user_secret("user secret"),
        window_close("window close"),
        window_close_outline("window close outline"),
        window_maximize("window maximize"),
        window_maximize_outline("window maximize outline"),
        window_minimize("window minimize"),
        window_minimize_outline("window minimize outline"),
        window_restore("window restore"),
        window_restore_outline("window restore outline"),

        // Communication /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        at("at"),
        bell("bell"),
        bell_outline("bell outline"),
        bell_slash("bell slash"),
        bell_slash_outline("bell slash outline"),
        comment("comment"),
        comment_outline("comment outline"),
        comment_alternate("comment alternate"),
        comment_alternate_outline("comment alternate outline"),
        comments("comments"),
        comments_outline("comments outline"),
        inbox("inbox"),
        language("language"),
        mobile("mobile"),
        mobile_alternate("mobile alternate"),
        paper_plane("paper plane"),
        paper_plane_outline("paper plane outline"),
        wifi("wifi"),

        // Computers ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        desktop("desktop"),
        hdd("hdd"),
        hdd_outline("hdd outline"),
        keyboard("keyboard"),
        keyboard_outline("keyboard outline"),
        laptop("laptop"),
        microchip("microchip"),
        plug("plug"),
        power_off("power off"),
        print("print"),
        server("server"),
        settings("settings"),
        tablet("tablet"),
        tablet_alternate("tablet alternate"),
        tv("tv"),

        // Currency ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        dollar_sign("dollar sign"),
        euro_sign("euro sign"),
        lira_sign("lira sign"),
        money_bill_alternate("money bill alternate"),
        money_bill_alternate_outline("money bill alternate outline"),
        pound_sign("pound sign"),
        ruble_sign("ruble sign"),
        rupee_sign("rupee sign"),
        shekel_sign("shekel sign"),
        won_sign("won sign"),
        yen_sign("yen sign"),

        // Date & Time //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        calendar_check("calendar check"),
        calendar_check_outline("calendar check outline"),
        calendar_minus("calendar minus"),
        calendar_minus_outline("calendar minus outline"),
        calendar_plus("calendar plus"),
        calendar_plus_outline("calendar plus outline"),
        calendar_times("calendar times"),
        calendar_times_outline("calendar times outline"),
        clock("clock"),
        clock_outline("clock outline"),
        hourglass("hourglass"),
        hourglass_outline("hourglass outline"),
        hourglass_end("hourglass end"),
        hourglass_half("hourglass half"),
        hourglass_start("hourglass start"),
        stopwatch("stopwatch"),

        // Design ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        adjust("adjust"),
        cloneIcon("clone"),
        clone_outline("clone outline"),
        crop("crop"),
        crosshairs("crosshairs"),
        dropdown("dropdown"),
        eye("eye"),
        eye_dropper("eye dropper"),
        eye_slash("eye slash"),
        eye_slash_outline("eye slash outline"),
        object_group("object group"),
        object_group_outline("object group outline"),
        object_ungroup("object ungroup"),
        object_ungroup_outline("object ungroup outline"),
        paint_brush("paint brush"),
        tint("tint"),

        // Editors //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        align_center("align center"),
        align_justify("align justify"),
        align_left("align left"),
        align_right("align right"),
        bold("bold"),
        font("font"),
        heading("heading"),
        i_cursor("i cursor"),
        indent("indent"),
        italic("italic"),
        linkify("linkify"),
        list("list"),
        list_alternate("list alternate"),
        list_alternate_outline("list alternate outline"),
        list_ol("list ol"),
        list_ul("list ul"),
        outdent("outdent"),
        paragraph("paragraph"),
        quote_left("quote left"),
        quote_right("quote right"),
        strikethrough("strikethrough"),
        subscript("subscript"),
        superscript("superscript"),
        th("th"),
        th_large("th large"),
        th_list("th list"),
        trash("trash"),
        trash_alternate("trash alternate"),
        trash_alternate_outline("trash alternate outline"),
        underline("underline"),
        unlink("unlink"),

        // Files ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        file_archive("file archive"),
        file_archive_outline("file archive outline"),
        file_excel("file excel"),
        file_excel_outline("file excel outline"),
        file_image("file image"),
        file_image_outline("file image outline"),
        file_pdf("file pdf"),
        file_pdf_outline("file pdf outline"),
        file_powerpoint("file powerpoint"),
        file_powerpoint_outline("file powerpoint outline"),
        file_word("file word"),
        file_word_outline("file word outline"),

        // Genders //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        genderless("genderless"),
        mars("mars"),
        mars_double("mars double"),
        mars_stroke("mars stroke"),
        mars_stroke_horizontal("mars stroke horizontal"),
        mars_stroke_vertical("mars stroke vertical"),
        mercury("mercury"),
        neuter("neuter"),
        transgender("transgender"),
        transgender_alternate("transgender alternate"),
        venus("venus"),
        venus_double("venus double"),
        venus_mars("venus mars"),

        // Hands & Gestures /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        hand_lizard("hand lizard"),
        hand_lizard_outline("hand lizard outline"),
        hand_paper("hand paper"),
        hand_paper_outline("hand paper outline"),
        hand_peace("hand peace"),
        hand_peace_outline("hand peace outline"),
        hand_rock("hand rock"),
        hand_rock_outline("hand rock outline"),
        hand_scissors("hand scissors"),
        hand_scissors_outline("hand scissors outline"),
        hand_spock("hand spock"),
        hand_spock_outline("hand spock outline"),
        handshake("handshake"),
        handshake_outline("handshake outline"),
        thumbs_down("thumbs down"),
        thumbs_down_outline("thumbs down outline"),
        thumbs_up("thumbs up"),
        thumbs_up_outline("thumbs up outline"),

        // Health ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ambulance("ambulance"),
        h_square("h square"),
        heart("heart"),
        heart_outline("heart outline"),
        heartbeat("heartbeat"),
        hospital("hospital"),
        hospital_outline("hospital outline"),
        medkit("medkit"),
        plus_square("plus square"),
        plus_square_outline("plus square outline"),
        stethoscope("stethoscope"),
        user_md("user md"),

        // Images ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        bolt("bolt"),
        camera("camera"),
        camera_retro("camera retro"),
        id_badge("id badge"),
        id_badge_outline("id badge outline"),
        id_card("id card"),
        id_card_outline("id card outline"),
        image("image"),
        image_outline("image outline"),
        images("images"),
        images_outline("images outline"),
        sliders_horizontal("sliders horizontal"),

        // Interfaces ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ban("ban"),
        bars("bars"),
        beer("beer"),
        check("check"),
        check_circle("check circle"),
        check_circle_outline("check circle outline"),
        check_square("check square"),
        check_square_outline("check square outline"),
        close("close"),
        cloud("cloud"),
        cog("cog"),
        cogs("cogs"),
        database("database"),
        dot_circle("dot circle"),
        dot_circle_outline("dot circle outline"),
        ellipsis_horizontal("ellipsis horizontal"),
        ellipsis_vertical("ellipsis vertical"),
        exclamation("exclamation"),
        exclamation_circle("exclamation circle"),
        exclamation_triangle("exclamation triangle"),
        flag("flag"),
        flag_outline("flag outline"),
        flag_checkered("flag checkered"),
        frown("frown"),
        frown_outline("frown outline"),
        grip_horizontal("grip horizontal"),
        grip_lines("grip lines"),
        grip_lines_vertical("grip lines vertical"),
        grip_vertical("grip vertical"),
        hashtag("hashtag"),
        home("home"),
        info("info"),
        info_circle("info circle"),
        magic("magic"),
        meh("meh"),
        meh_outline("meh outline"),
        minus("minus"),
        minus_circle("minus circle"),
        minus_square("minus square"),
        minus_square_outline("minus square outline"),
        plus("plus"),
        plus_circle("plus circle"),
        question("question"),
        search("search"),
        search_minus("search minus"),
        search_plus("search plus"),
        share_alternate("share alternate"),
        share_alternate_square("share alternate square"),
        signal("signal"),
        smile("smile"),
        smile_outline("smile outline"),
        star("star"),
        star_outline("star outline"),
        star_half("star half"),
        star_half_outline("star half outline"),
        times("times"),
        times_circle("times circle"),
        times_circle_outline("times circle outline"),
        toggle_off("toggle off"),
        toggle_on("toggle on"),
        trophy("trophy"),
        user("user"),
        user_outline("user outline"),
        user_circle("user circle"),
        user_circle_outline("user circle outline"),
        user_shield("user shield"),

        // Logistics ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        box("box"),
        boxes("boxes"),
        clipboard_check("clipboard check"),
        clipboard_list("clipboard list"),
        dolly("dolly"),
        dolly_flatbed("dolly flatbed"),
        pallet("pallet"),
        shipping_fast("shipping fast"),
        truck("truck"),
        warehouse("warehouse"),

        // Maps /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        anchor("anchor"),
        bed("bed"),
        bicycle("bicycle"),
        binoculars("binoculars"),
        bomb("bomb"),
        bookmark("bookmark"),
        bookmark_outline("bookmark outline"),
        car("car"),
        fighter_jet("fighter jet"),
        fire("fire"),
        flask("flask"),
        gamepad("gamepad"),
        gavel("gavel"),
        gift("gift"),
        glass_martini("glass martini"),
        graduation_cap("graduation cap"),
        key("key"),
        leaf("leaf"),
        lemon("lemon"),
        lemon_outline("lemon outline"),
        life_ring("life ring"),
        life_ring_outline("life ring outline"),
        lightbulb("lightbulb"),
        lightbulb_outline("lightbulb outline"),
        magnet("magnet"),
        male("male"),
        map("map"),
        map_outline("map outline"),
        map_marker("map marker"),
        map_marker_alternate("map marker alternate"),
        map_pin("map pin"),
        map_signs("map signs"),
        motorcycle("motorcycle"),
        newspaper("newspaper"),
        newspaper_outline("newspaper outline"),
        paw("paw"),
        plane("plane"),
        road("road"),
        rocket("rocket"),
        ship("ship"),
        shopping_bag("shopping bag"),
        shopping_basket("shopping basket"),
        shop("shop"),
        shopping_cart("shopping cart"),
        shower("shower"),
        street_view("street view"),
        subway("subway"),
        taxi("taxi"),
        ticket_alternate("ticket alternate"),
        train("train"),
        tree("tree"),
        umbrella("umbrella"),
        university("university"),
        utensil_spoon("utensil spoon"),
        utensils("utensils"),
        wrench("wrench"),

        // Medical //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        band_aid("band aid"),
        dna("dna"),
        first_aid("first aid"),
        hospital_symbol("hospital symbol"),
        pills("pills"),
        syringe("syringe"),
        thermometer("thermometer"),
        weight("weight"),

        // Objects //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        bus("bus"),
        cube("cube"),
        cubes("cubes"),
        futbol("futbol"),
        futbol_outline("futbol outline"),
        gem("gem"),
        gem_outline("gem outline"),
        lock("lock"),
        lock_open("lock open"),
        moon("moon"),
        moon_outline("moon outline"),
        puzzle_piece("puzzle piece"),
        snowflake("snowflake"),
        snowflake_outline("snowflake outline"),
        space_shuttle("space shuttle"),
        sun("sun"),
        sun_outline("sun outline"),
        tachometer_alternate("tachometer alternate"),
        unlock("unlock"),
        unlock_alternate("unlock alternate"),

        // Payments & Shopping //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        cart_plus("cart plus"),
        credit_card("credit card"),
        credit_card_outline("credit card outline"),

        // Shapes ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        square("square"),
        square_outline("square outline"),

        // Spinners /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        asterisk("asterisk"),
        circle_notch("circle notch"),
        spinner("spinner"),

        // Sports ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        baseball_ball("baseball ball"),
        basketball_ball("basketball ball"),
        bowling_ball("bowling ball"),
        football_ball("football ball"),
        golf_ball("golf ball"),
        hockey_puck("hockey puck"),
        quidditch("quidditch"),
        table_tennis("table tennis"),
        volleyball_ball("volleyball ball"),

        // Status ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        battery_empty("battery empty"),
        battery_full("battery full"),
        battery_half("battery half"),
        battery_quarter("battery quarter"),
        battery_three_quarters("battery three quarters"),
        thermometer_empty("thermometer empty"),
        thermometer_full("thermometer full"),
        thermometer_half("thermometer half"),
        thermometer_quarter("thermometer quarter"),
        thermometer_three_quarters("thermometer three quarters"),

        // User & People ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        child("child"),
        female("female"),
        user_plus("user plus"),
        user_times("user times"),
        users("users"),

        // Brands ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        _500px("500px"),
        accessible_icon("accessible icon"),
        accusoft("accusoft"),
        adn("adn"),
        adversal("adversal"),
        affiliatetheme("affiliatetheme"),
        algolia("algolia"),
        amazon("amazon"),
        amazon_pay("amazon pay"),
        amilia("amilia"),
        android("android"),
        angellist("angellist"),
        angrycreative("angrycreative"),
        angular("angular"),
        app_store("app store"),
        app_store_ios("app store ios"),
        apper("apper"),
        apple("apple"),
        apple_pay("apple pay"),
        asymmetrik("asymmetrik"),
        audible("audible"),
        autoprefixer("autoprefixer"),
        avianex("avianex"),
        aviato("aviato"),
        aws("aws"),
        bandcamp("bandcamp"),
        behance("behance"),
        behance_square("behance square"),
        bimobject("bimobject"),
        bitbucket("bitbucket"),
        bitcoin("bitcoin"),
        bity("bity"),
        black_tie("black tie"),
        blackberry("blackberry"),
        blogger("blogger"),
        blogger_b("blogger b"),
        bluetooth("bluetooth"),
        bluetooth_b("bluetooth b"),
        btc("btc"),
        buromobelexperte("buromobelexperte"),
        buysellads("buysellads"),
        cc_amazon_pay("cc amazon pay"),
        cc_amex("cc amex"),
        cc_apple_pay("cc apple pay"),
        cc_diners_club("cc diners club"),
        cc_discover("cc discover"),
        cc_jcb("cc jcb"),
        cc_mastercard("cc mastercard"),
        cc_paypal("cc paypal"),
        cc_stripe("cc stripe"),
        cc_visa("cc visa"),
        centercode("centercode"),
        chrome("chrome"),
        cloudscale("cloudscale"),
        cloudsmith("cloudsmith"),
        cloudversify("cloudversify"),
        codepen("codepen"),
        codiepie("codiepie"),
        connectdevelop("connectdevelop"),
        contao("contao"),
        cpanel("cpanel"),
        creative_commons("creative commons"),
        css3("css3"),
        css3_alternate("css3 alternate"),
        cuttlefish("cuttlefish"),
        d_and_d("d and d"),
        dashcube("dashcube"),
        delicious("delicious"),
        deploydog("deploydog"),
        deskpro("deskpro"),
        deviantart("deviantart"),
        digg("digg"),
        digital_ocean("digital ocean"),
        discord("discord"),
        discourse("discourse"),
        dochub("dochub"),
        docker("docker"),
        draft2digital("draft2digital"),
        dribbble("dribbble"),
        dribbble_square("dribbble square"),
        dropbox("dropbox"),
        drupal("drupal"),
        dyalog("dyalog"),
        earlybirds("earlybirds"),
        edge("edge"),
        elementor("elementor"),
        ember("ember"),
        empire("empire"),
        envira("envira"),
        erlang("erlang"),
        ethereum("ethereum"),
        etsy("etsy"),
        expeditedssl("expeditedssl"),
        facebook("facebook"),
        facebook_f("facebook f"),
        facebook_messenger("facebook messenger"),
        facebook_square("facebook square"),
        firefox("firefox"),
        first_order("first order"),
        firstdraft("firstdraft"),
        flickr("flickr"),
        flipboard("flipboard"),
        fly("fly"),
        font_awesome("font awesome"),
        font_awesome_alternate("font awesome alternate"),
        font_awesome_flag("font awesome flag"),
        fonticons("fonticons"),
        fonticons_fi("fonticons fi"),
        fort_awesome("fort awesome"),
        fort_awesome_alternate("fort awesome alternate"),
        forumbee("forumbee"),
        foursquare("foursquare"),
        free_code_camp("free code camp"),
        freebsd("freebsd"),
        get_pocket("get pocket"),
        gg("gg"),
        gg_circle("gg circle"),
        git("git"),
        git_square("git square"),
        github("github"),
        github_alternate("github alternate"),
        github_square("github square"),
        gitkraken("gitkraken"),
        gitlab("gitlab"),
        gitter("gitter"),
        glide("glide"),
        glide_g("glide g"),
        gofore("gofore"),
        goodreads("goodreads"),
        goodreads_g("goodreads g"),
        google("google"),
        google_drive("google drive"),
        google_play("google play"),
        google_plus("google plus"),
        google_plus_g("google plus g"),
        google_plus_square("google plus square"),
        google_wallet("google wallet"),
        gratipay("gratipay"),
        grav("grav"),
        gripfire("gripfire"),
        grunt("grunt"),
        gulp("gulp"),
        hacker_news("hacker news"),
        hacker_news_square("hacker news square"),
        hips("hips"),
        hire_a_helper("hire a helper"),
        hooli("hooli"),
        hotjar("hotjar"),
        houzz("houzz"),
        html5("html5"),
        hubspot("hubspot"),
        imdb("imdb"),
        instagram("instagram"),
        internet_explorer("internet explorer"),
        ioxhost("ioxhost"),
        itunes("itunes"),
        itunes_note("itunes note"),
        jenkins("jenkins"),
        joget("joget"),
        joomla("joomla"),
        js("js"),
        js_square("js square"),
        jsfiddle("jsfiddle"),
        keycdn("keycdn"),
        kickstarter("kickstarter"),
        kickstarter_k("kickstarter k"),
        korvue("korvue"),
        laravel("laravel"),
        lastfm("lastfm"),
        lastfm_square("lastfm square"),
        leanpub("leanpub"),
        less("less"),
        linechat("linechat"),
        linkedin("linkedin"),
        linkedin_in("linkedin in"),
        linode("linode"),
        linux("linux"),
        lyft("lyft"),
        magento("magento"),
        maxcdn("maxcdn"),
        medapps("medapps"),
        medium("medium"),
        medium_m("medium m"),
        medrt("medrt"),
        meetup("meetup"),
        microsoft("microsoft"),
        mix("mix"),
        mixcloud("mixcloud"),
        mizuni("mizuni"),
        modx("modx"),
        monero("monero"),
        napster("napster"),
        nintendo_switch("nintendo switch"),
        node("node"),
        node_js("node js"),
        npm("npm"),
        ns8("ns8"),
        nutritionix("nutritionix"),
        odnoklassniki("odnoklassniki"),
        odnoklassniki_square("odnoklassniki square"),
        opencart("opencart"),
        openid("openid"),
        opera("opera"),
        optin_monster("optin monster"),
        osi("osi"),
        page4("page4"),
        pagelines("pagelines"),
        palfed("palfed"),
        patreon("patreon"),
        paypal("paypal"),
        periscope("periscope"),
        phabricator("phabricator"),
        phoenix_framework("phoenix framework"),
        php("php"),
        pied_piper("pied piper"),
        pied_piper_alternate("pied piper alternate"),
        pied_piper_pp("pied piper pp"),
        pinterest("pinterest"),
        pinterest_p("pinterest p"),
        pinterest_square("pinterest square"),
        playstation("playstation"),
        product_hunt("product hunt"),
        pushed("pushed"),
        python("python"),
        qq("qq"),
        quinscape("quinscape"),
        quora("quora"),
        ravelry("ravelry"),
        react("react"),
        rebel("rebel"),
        redriver("redriver"),
        reddit("reddit"),
        reddit_alien("reddit alien"),
        reddit_square("reddit square"),
        rendact("rendact"),
        renren("renren"),
        replyd("replyd"),
        resolving("resolving"),
        rocketchat("rocketchat"),
        rockrms("rockrms"),
        safari("safari"),
        sass("sass"),
        schlix("schlix"),
        scribd("scribd"),
        searchengin("searchengin"),
        sellcast("sellcast"),
        sellsy("sellsy"),
        servicestack("servicestack"),
        shirtsinbulk("shirtsinbulk"),
        simplybuilt("simplybuilt"),
        sistrix("sistrix"),
        skyatlas("skyatlas"),
        skype("skype"),
        slack("slack"),
        slack_hash("slack hash"),
        slideshare("slideshare"),
        snapchat("snapchat"),
        snapchat_ghost("snapchat ghost"),
        snapchat_square("snapchat square"),
        soundcloud("soundcloud"),
        speakap("speakap"),
        spotify("spotify"),
        stack_exchange("stack exchange"),
        stack_overflow("stack overflow"),
        staylinked("staylinked"),
        steam("steam"),
        steam_square("steam square"),
        steam_symbol("steam symbol"),
        sticker_mule("sticker mule"),
        strava("strava"),
        stripe("stripe"),
        stripe_s("stripe s"),
        studiovinari("studiovinari"),
        stumbleupon("stumbleupon"),
        stumbleupon_circle("stumbleupon circle"),
        superpowers("superpowers"),
        supple("supple"),
        telegram("telegram"),
        telegram_plane("telegram plane"),
        tencent_weibo("tencent weibo"),
        themeisle("themeisle"),
        trello("trello"),
        tripadvisor("tripadvisor"),
        tumblr("tumblr"),
        tumblr_square("tumblr square"),
        twitch("twitch"),
        twitter("twitter"),
        twitter_square("twitter square"),
        typo3("typo3"),
        uber("uber"),
        uikit("uikit"),
        uniregistry("uniregistry"),
        untappd("untappd"),
        usb("usb"),
        ussunnah("ussunnah"),
        vaadin("vaadin"),
        viacoin("viacoin"),
        viadeo("viadeo"),
        viadeo_square("viadeo square"),
        viber("viber"),
        vimeo("vimeo"),
        vimeo_square("vimeo square"),
        vimeo_v("vimeo v"),
        vine("vine"),
        vk("vk"),
        vnv("vnv"),
        vuejs("vuejs"),
        wechat("wechat"),
        weibo("weibo"),
        weixin("weixin"),
        whatsapp("whatsapp"),
        whatsapp_square("whatsapp square"),
        whmcs("whmcs"),
        wikipedia_w("wikipedia w"),
        windows("windows"),
        wordpress("wordpress"),
        wordpress_simple("wordpress simple"),
        wpbeginner("wpbeginner"),
        wpexplorer("wpexplorer"),
        wpforms("wpforms"),
        xbox("xbox"),
        xing("xing"),
        xing_square("xing square"),
        y_combinator("y combinator"),
        yahoo("yahoo"),
        yandex("yandex"),
        yandex_international("yandex international"),
        yelp("yelp"),
        yoast("yoast"),
        youtube("youtube"),
        youtube_square("youtube square")
    }

    private val cssClasses = mutableListOf<String>()

    private operator fun plus(cls: String) = apply { cssClasses.add(cls) }

    private operator fun plus(classes: Array<out String>) = apply { cssClasses.addAll(classes) }

    fun render(classes: String, block: I.() -> Unit = {}): Unit = when {
        cssClasses.isEmpty() -> parent.i(classes = classes)
        else -> parent.i(classes = cssClasses.plus(classes).joinToString(" ")) {
            block()
        }
    }

    fun render(name: Name, block: I.() -> Unit = {}) = render("${name.cls} icon", block)

    operator fun invoke(block: I.() -> Unit) = render("icon", block)

    @SemanticUiCssMarker fun custom(cls: String) = render("$cls icon")

    @SemanticUiCssMarker fun with(cls: String) = this + cls
    @SemanticUiCssMarker fun with(cls: String, block: I.() -> Unit) = (this + cls).render("", block)

    // conditional classes

    @SemanticUiConditionalMarker fun given(condition: Boolean, action: SemanticIcon.() -> SemanticIcon) = when (condition) {
        false -> this
        else -> this.action()
    }

    @SemanticUiConditionalMarker val then get() = this

    // coloring ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker fun color(color: SemanticColor) = when {
        color.isSet -> with(color.toString())
        else -> this
    }

    @SemanticUiCssMarker val red get() = this + "red"
    @SemanticUiCssMarker val orange get() = this + "orange"
    @SemanticUiCssMarker val yellow get() = this + "yellow"
    @SemanticUiCssMarker val olive get() = this + "olive"
    @SemanticUiCssMarker val green get() = this + "green"
    @SemanticUiCssMarker val teal get() = this + "teal"
    @SemanticUiCssMarker val blue get() = this + "blue"
    @SemanticUiCssMarker val violet get() = this + "violet"
    @SemanticUiCssMarker val purple get() = this + "purple"
    @SemanticUiCssMarker val pink get() = this + "pink"
    @SemanticUiCssMarker val brown get() = this + "brown"
    @SemanticUiCssMarker val grey get() = this + "grey"
    @SemanticUiCssMarker val black get() = this + "black"

    @SemanticUiCssMarker val inverted get() = this + "inverted"
    @SemanticUiCssMarker val circular get() = this + "circular"

    // Alignment ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker val middle get() = this + "middle"
    @SemanticUiCssMarker val aligned get() = this + "aligned"

    // Behaviour ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker val disabled get() = this + "disabled"
    @SemanticUiCssMarker val link get() = this + "link"

    // Size ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker val mini get() = this + "mini"
    @SemanticUiCssMarker val tiny get() = this + "tiny"
    @SemanticUiCssMarker val small get() = this + "small"
    @SemanticUiCssMarker val medium get() = this + "medium"
    @SemanticUiCssMarker val large get() = this + "large"
    @SemanticUiCssMarker val big get() = this + "big"
    @SemanticUiCssMarker val huge get() = this + "huge"
    @SemanticUiCssMarker val massive get() = this + "massive"

    // MORE ...

    @SemanticIconMarker fun couch() = render("couch icon")
    @SemanticIconMarker fun broom() = render("broom icon")
    @SemanticIconMarker fun glasses() = render("glasses icon")

    // Accessibility ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticIconMarker fun american_sign_language_interpreting() = render(Name.american_sign_language_interpreting)

    @SemanticIconMarker fun assistive_listening_systems() = render(Name.assistive_listening_systems)
    @SemanticIconMarker fun audio_description() = render(Name.audio_description)
    @SemanticIconMarker fun blind() = render(Name.blind)
    @SemanticIconMarker fun braille() = render(Name.braille)
    @SemanticIconMarker fun closed_captioning() = render(Name.closed_captioning)
    @SemanticIconMarker fun closed_captioning_outline() = render(Name.closed_captioning_outline)
    @SemanticIconMarker fun deaf() = render(Name.deaf)
    @SemanticIconMarker fun low_vision() = render(Name.low_vision)
    @SemanticIconMarker fun phone_volume() = render(Name.phone_volume)
    @SemanticIconMarker fun question_circle() = render(Name.question_circle)
    @SemanticIconMarker fun question_circle_outline() = render(Name.question_circle_outline)
    @SemanticIconMarker fun sign_language() = render(Name.sign_language)
    @SemanticIconMarker fun tty() = render(Name.tty)
    @SemanticIconMarker fun universal_access() = render(Name.universal_access)
    @SemanticIconMarker fun wheelchair() = render(Name.wheelchair)

    // Arrows ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticIconMarker fun angle_double_down() = render(Name.angle_double_down)
    @SemanticIconMarker fun angle_double_left() = render(Name.angle_double_left)
    @SemanticIconMarker fun angle_double_right() = render(Name.angle_double_right)
    @SemanticIconMarker fun angle_double_up() = render(Name.angle_double_up)
    @SemanticIconMarker fun angle_down() = render(Name.angle_down)
    @SemanticIconMarker fun angle_left() = render(Name.angle_left)
    @SemanticIconMarker fun angle_right() = render(Name.angle_right)
    @SemanticIconMarker fun angle_up() = render(Name.angle_up)
    @SemanticIconMarker fun arrow_alternate_circle_down() = render(Name.arrow_alternate_circle_down)
    @SemanticIconMarker fun arrow_alternate_circle_down_outline() = render(Name.arrow_alternate_circle_down_outline)
    @SemanticIconMarker fun arrow_alternate_circle_left() = render(Name.arrow_alternate_circle_left)
    @SemanticIconMarker fun arrow_alternate_circle_left_outline() = render(Name.arrow_alternate_circle_left_outline)
    @SemanticIconMarker fun arrow_alternate_circle_right() = render(Name.arrow_alternate_circle_right)
    @SemanticIconMarker fun arrow_alternate_circle_right_outline() = render(Name.arrow_alternate_circle_right_outline)
    @SemanticIconMarker fun arrow_alternate_circle_up() = render(Name.arrow_alternate_circle_up)
    @SemanticIconMarker fun arrow_alternate_circle_up_outline() = render(Name.arrow_alternate_circle_up_outline)
    @SemanticIconMarker fun arrow_circle_down() = render(Name.arrow_circle_down)
    @SemanticIconMarker fun arrow_circle_left() = render(Name.arrow_circle_left)
    @SemanticIconMarker fun arrow_circle_right() = render(Name.arrow_circle_right)
    @SemanticIconMarker fun arrow_circle_up() = render(Name.arrow_circle_up)
    @SemanticIconMarker fun arrow_down() = render(Name.arrow_down)
    @SemanticIconMarker fun arrow_left() = render(Name.arrow_left)
    @SemanticIconMarker fun arrow_right() = render(Name.arrow_right)
    @SemanticIconMarker fun arrow_up() = render(Name.arrow_up)
    @SemanticIconMarker fun arrows_alternate() = render(Name.arrows_alternate)
    @SemanticIconMarker fun arrows_alternate_horizontal() = render(Name.arrows_alternate_horizontal)
    @SemanticIconMarker fun arrows_alternate_vertical() = render(Name.arrows_alternate_vertical)
    @SemanticIconMarker fun caret_down() = render(Name.caret_down)
    @SemanticIconMarker fun caret_left() = render(Name.caret_left)
    @SemanticIconMarker fun caret_right() = render(Name.caret_right)
    @SemanticIconMarker fun caret_square_down() = render(Name.caret_square_down)
    @SemanticIconMarker fun caret_square_down_outline() = render(Name.caret_square_down_outline)
    @SemanticIconMarker fun caret_square_left() = render(Name.caret_square_left)
    @SemanticIconMarker fun caret_square_left_outline() = render(Name.caret_square_left_outline)
    @SemanticIconMarker fun caret_square_right() = render(Name.caret_square_right)
    @SemanticIconMarker fun caret_square_right_outline() = render(Name.caret_square_right_outline)
    @SemanticIconMarker fun caret_square_up() = render(Name.caret_square_up)
    @SemanticIconMarker fun caret_square_up_outline() = render(Name.caret_square_up_outline)
    @SemanticIconMarker fun caret_up() = render(Name.caret_up)
    @SemanticIconMarker fun cart_arrow_down() = render(Name.cart_arrow_down)
    @SemanticIconMarker fun chart_line() = render(Name.chart_line)
    @SemanticIconMarker fun chevron_circle_down() = render(Name.chevron_circle_down)
    @SemanticIconMarker fun chevron_circle_left() = render(Name.chevron_circle_left)
    @SemanticIconMarker fun chevron_circle_right() = render(Name.chevron_circle_right)
    @SemanticIconMarker fun chevron_circle_up() = render(Name.chevron_circle_up)
    @SemanticIconMarker fun chevron_down() = render(Name.chevron_down)
    @SemanticIconMarker fun chevron_left() = render(Name.chevron_left)
    @SemanticIconMarker fun chevron_right() = render(Name.chevron_right)
    @SemanticIconMarker fun chevron_up() = render(Name.chevron_up)
    @SemanticIconMarker fun cloud_download() = render(Name.cloud_download)
    @SemanticIconMarker fun cloud_upload() = render(Name.cloud_upload)
    @SemanticIconMarker fun download() = render(Name.download)
    @SemanticIconMarker fun exchange_alternate() = render(Name.exchange_alternate)
    @SemanticIconMarker fun expand_arrows_alternate() = render(Name.expand_arrows_alternate)
    @SemanticIconMarker fun external_alternate() = render(Name.external_alternate)
    @SemanticIconMarker fun external_square_alternate() = render(Name.external_square_alternate)
    @SemanticIconMarker fun hand_point_down() = render(Name.hand_point_down)
    @SemanticIconMarker fun hand_point_down_outline() = render(Name.hand_point_down_outline)
    @SemanticIconMarker fun hand_point_left() = render(Name.hand_point_left)
    @SemanticIconMarker fun hand_point_left_outline() = render(Name.hand_point_left_outline)
    @SemanticIconMarker fun hand_point_right() = render(Name.hand_point_right)
    @SemanticIconMarker fun hand_point_right_outline() = render(Name.hand_point_right_outline)
    @SemanticIconMarker fun hand_point_up() = render(Name.hand_point_up)
    @SemanticIconMarker fun hand_point_up_outline() = render(Name.hand_point_up_outline)
    @SemanticIconMarker fun hand_pointer() = render(Name.hand_pointer)
    @SemanticIconMarker fun hand_pointer_outline() = render(Name.hand_pointer_outline)
    @SemanticIconMarker fun history() = render(Name.history)
    @SemanticIconMarker fun level_down_alternate() = render(Name.level_down_alternate)
    @SemanticIconMarker fun level_up_alternate() = render(Name.level_up_alternate)
    @SemanticIconMarker fun location_arrow() = render(Name.location_arrow)
    @SemanticIconMarker fun long_arrow_alternate_down() = render(Name.long_arrow_alternate_down)
    @SemanticIconMarker fun long_arrow_alternate_left() = render(Name.long_arrow_alternate_left)
    @SemanticIconMarker fun long_arrow_alternate_right() = render(Name.long_arrow_alternate_right)
    @SemanticIconMarker fun long_arrow_alternate_up() = render(Name.long_arrow_alternate_up)
    @SemanticIconMarker fun mouse_pointer() = render(Name.mouse_pointer)
    @SemanticIconMarker fun play() = render(Name.play)
    @SemanticIconMarker fun random() = render(Name.random)
    @SemanticIconMarker fun recycle() = render(Name.recycle)
    @SemanticIconMarker fun redo() = render(Name.redo)
    @SemanticIconMarker fun redo_alternate() = render(Name.redo_alternate)
    @SemanticIconMarker fun reply() = render(Name.reply)
    @SemanticIconMarker fun reply_all() = render(Name.reply_all)
    @SemanticIconMarker fun retweet() = render(Name.retweet)
    @SemanticIconMarker fun share() = render(Name.share)
    @SemanticIconMarker fun share_square() = render(Name.share_square)
    @SemanticIconMarker fun share_square_outline() = render(Name.share_square_outline)
    @SemanticIconMarker fun sign_in_alternate() = render(Name.sign_in_alternate)
    @SemanticIconMarker fun sign_out_alternate() = render(Name.sign_out_alternate)
    @SemanticIconMarker fun sort() = render(Name.sort)
    @SemanticIconMarker fun sort_alphabet_down() = render(Name.sort_alphabet_down)
    @SemanticIconMarker fun sort_alphabet_up() = render(Name.sort_alphabet_up)
    @SemanticIconMarker fun sort_amount_down() = render(Name.sort_amount_down)
    @SemanticIconMarker fun sort_amount_up() = render(Name.sort_amount_up)
    @SemanticIconMarker fun sort_down() = render(Name.sort_down)
    @SemanticIconMarker fun sort_numeric_down() = render(Name.sort_numeric_down)
    @SemanticIconMarker fun sort_numeric_up() = render(Name.sort_numeric_up)
    @SemanticIconMarker fun sort_up() = render(Name.sort_up)
    @SemanticIconMarker fun sync() = render(Name.sync)
    @SemanticIconMarker fun sync_alternate() = render(Name.sync_alternate)
    @SemanticIconMarker fun text_height() = render(Name.text_height)
    @SemanticIconMarker fun text_width() = render(Name.text_width)
    @SemanticIconMarker fun undo() = render(Name.undo)
    @SemanticIconMarker fun undo_alternate() = render(Name.undo_alternate)
    @SemanticIconMarker fun upload() = render(Name.upload)

    // Audio and Video ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticIconMarker fun backward() = render(Name.backward)
    @SemanticIconMarker fun circle() = render(Name.circle)
    @SemanticIconMarker fun circle_outline() = render(Name.circle_outline)
    @SemanticIconMarker fun compress() = render(Name.compress)
    @SemanticIconMarker fun eject() = render(Name.eject)
    @SemanticIconMarker fun expand() = render(Name.expand)
    @SemanticIconMarker fun fast_backward() = render(Name.fast_backward)
    @SemanticIconMarker fun fast_forward() = render(Name.fast_forward)
    @SemanticIconMarker fun file_audio() = render(Name.file_audio)
    @SemanticIconMarker fun file_audio_outline() = render(Name.file_audio_outline)
    @SemanticIconMarker fun file_video() = render(Name.file_video)
    @SemanticIconMarker fun file_video_outline() = render(Name.file_video_outline)
    @SemanticIconMarker fun film() = render(Name.film)
    @SemanticIconMarker fun forward() = render(Name.forward)
    @SemanticIconMarker fun headphones() = render(Name.headphones)
    @SemanticIconMarker fun microphone() = render(Name.microphone)
    @SemanticIconMarker fun microphone_slash() = render(Name.microphone_slash)
    @SemanticIconMarker fun music() = render(Name.music)
    @SemanticIconMarker fun pause() = render(Name.pause)
    @SemanticIconMarker fun pause_circle() = render(Name.pause_circle)
    @SemanticIconMarker fun pause_circle_outline() = render(Name.pause_circle_outline)
    @SemanticIconMarker fun play_circle() = render(Name.play_circle)
    @SemanticIconMarker fun play_circle_outline() = render(Name.play_circle_outline)
    @SemanticIconMarker fun podcast() = render(Name.podcast)
    @SemanticIconMarker fun rss() = render(Name.rss)
    @SemanticIconMarker fun rss_square() = render(Name.rss_square)
    @SemanticIconMarker fun step_backward() = render(Name.step_backward)
    @SemanticIconMarker fun step_forward() = render(Name.step_forward)
    @SemanticIconMarker fun stop() = render(Name.stop)
    @SemanticIconMarker fun stop_circle() = render(Name.stop_circle)
    @SemanticIconMarker fun stop_circle_outline() = render(Name.stop_circle_outline)
    @SemanticIconMarker fun video() = render(Name.video)
    @SemanticIconMarker fun volume_down() = render(Name.volume_down)
    @SemanticIconMarker fun volume_off() = render(Name.volume_off)
    @SemanticIconMarker fun volume_up() = render(Name.volume_up)

    // Business //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticIconMarker fun address_book() = render(Name.address_book)
    @SemanticIconMarker fun address_book_outline() = render(Name.address_book_outline)
    @SemanticIconMarker fun address_card() = render(Name.address_card)
    @SemanticIconMarker fun address_card_outline() = render(Name.address_card_outline)
    @SemanticIconMarker fun archive() = render(Name.archive)
    @SemanticIconMarker fun balance_scale() = render(Name.balance_scale)
    @SemanticIconMarker fun birthday_cake() = render(Name.birthday_cake)
    @SemanticIconMarker fun book() = render(Name.book)
    @SemanticIconMarker fun briefcase() = render(Name.briefcase)
    @SemanticIconMarker fun building() = render(Name.building)
    @SemanticIconMarker fun building_outline() = render(Name.building_outline)
    @SemanticIconMarker fun bullhorn() = render(Name.bullhorn)
    @SemanticIconMarker fun bullseye() = render(Name.bullseye)
    @SemanticIconMarker fun calculator() = render(Name.calculator)
    @SemanticIconMarker fun calendar() = render(Name.calendar)
    @SemanticIconMarker fun calendar_outline() = render(Name.calendar_outline)
    @SemanticIconMarker fun calendar_alternate() = render(Name.calendar_alternate)
    @SemanticIconMarker fun calendar_alternate_outline() = render(Name.calendar_alternate_outline)
    @SemanticIconMarker fun certificate() = render(Name.certificate)
    @SemanticIconMarker fun chart_area() = render(Name.chart_area)
    @SemanticIconMarker fun chart_bar() = render(Name.chart_bar)
    @SemanticIconMarker fun chart_bar_outline() = render(Name.chart_bar_outline)
    @SemanticIconMarker fun chart_pie() = render(Name.chart_pie)
    @SemanticIconMarker fun clipboard() = render(Name.clipboard)
    @SemanticIconMarker fun clipboard_outline() = render(Name.clipboard_outline)
    @SemanticIconMarker fun coffee() = render(Name.coffee)
    @SemanticIconMarker fun columns() = render(Name.columns)
    @SemanticIconMarker fun compass() = render(Name.compass)
    @SemanticIconMarker fun compass_outline() = render(Name.compass_outline)
    @SemanticIconMarker fun copy() = render(Name.copy)
    @SemanticIconMarker fun copy_outline() = render(Name.copy_outline)
    @SemanticIconMarker fun copyright() = render(Name.copyright)
    @SemanticIconMarker fun copyright_outline() = render(Name.copyright_outline)
    @SemanticIconMarker fun cut() = render(Name.cut)
    @SemanticIconMarker fun edit() = render(Name.edit)
    @SemanticIconMarker fun edit_outline() = render(Name.edit_outline)
    @SemanticIconMarker fun envelope() = render(Name.envelope)
    @SemanticIconMarker fun envelope_outline() = render(Name.envelope_outline)
    @SemanticIconMarker fun envelope_open() = render(Name.envelope_open)
    @SemanticIconMarker fun envelope_open_outline() = render(Name.envelope_open_outline)
    @SemanticIconMarker fun envelope_square() = render(Name.envelope_square)
    @SemanticIconMarker fun eraser() = render(Name.eraser)
    @SemanticIconMarker fun fax() = render(Name.fax)
    @SemanticIconMarker fun file() = render(Name.file)
    @SemanticIconMarker fun file_outline() = render(Name.file_outline)
    @SemanticIconMarker fun file_alternate() = render(Name.file_alternate)
    @SemanticIconMarker fun file_alternate_outline() = render(Name.file_alternate_outline)
    @SemanticIconMarker fun folder() = render(Name.folder)
    @SemanticIconMarker fun folder_outline() = render(Name.folder_outline)
    @SemanticIconMarker fun folder_open() = render(Name.folder_open)
    @SemanticIconMarker fun folder_open_outline() = render(Name.folder_open_outline)
    @SemanticIconMarker fun globe() = render(Name.globe)
    @SemanticIconMarker fun industry() = render(Name.industry)
    @SemanticIconMarker fun paperclip() = render(Name.paperclip)
    @SemanticIconMarker fun paste() = render(Name.paste)
    @SemanticIconMarker fun pen_square() = render(Name.pen_square)
    @SemanticIconMarker fun pencil_alternate() = render(Name.pencil_alternate)
    @SemanticIconMarker fun percent() = render(Name.percent)
    @SemanticIconMarker fun phone() = render(Name.phone)
    @SemanticIconMarker fun phone_square() = render(Name.phone_square)
    @SemanticIconMarker fun registered() = render(Name.registered)
    @SemanticIconMarker fun registered_outline() = render(Name.registered_outline)
    @SemanticIconMarker fun save() = render(Name.save)
    @SemanticIconMarker fun save_outline() = render(Name.save_outline)
    @SemanticIconMarker fun sitemap() = render(Name.sitemap)
    @SemanticIconMarker fun sticky_note() = render(Name.sticky_note)
    @SemanticIconMarker fun sticky_note_outline() = render(Name.sticky_note_outline)
    @SemanticIconMarker fun suitcase() = render(Name.suitcase)
    @SemanticIconMarker fun table() = render(Name.table)
    @SemanticIconMarker fun tag() = render(Name.tag)
    @SemanticIconMarker fun tags() = render(Name.tags)
    @SemanticIconMarker fun tasks() = render(Name.tasks)
    @SemanticIconMarker fun thumbtack() = render(Name.thumbtack)
    @SemanticIconMarker fun trademark() = render(Name.trademark)

    // Code ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticIconMarker fun barcode() = render(Name.barcode)
    @SemanticIconMarker fun bath() = render(Name.bath)
    @SemanticIconMarker fun bug() = render(Name.bug)
    @SemanticIconMarker fun code() = render(Name.code)
    @SemanticIconMarker fun code_branch() = render(Name.code_branch)
    @SemanticIconMarker fun file_code() = render(Name.file_code)
    @SemanticIconMarker fun file_code_outline() = render(Name.file_code_outline)
    @SemanticIconMarker fun filter() = render(Name.filter)
    @SemanticIconMarker fun fire_extinguisher() = render(Name.fire_extinguisher)
    @SemanticIconMarker fun fork() = render(Name.fork)
    @SemanticIconMarker fun qrcode() = render(Name.qrcode)
    @SemanticIconMarker fun shield_alternate() = render(Name.shield_alternate)
    @SemanticIconMarker fun terminal() = render(Name.terminal)
    @SemanticIconMarker fun user_secret() = render(Name.user_secret)
    @SemanticIconMarker fun window_close() = render(Name.window_close)
    @SemanticIconMarker fun window_close_outline() = render(Name.window_close_outline)
    @SemanticIconMarker fun window_maximize() = render(Name.window_maximize)
    @SemanticIconMarker fun window_maximize_outline() = render(Name.window_maximize_outline)
    @SemanticIconMarker fun window_minimize() = render(Name.window_minimize)
    @SemanticIconMarker fun window_minimize_outline() = render(Name.window_minimize_outline)
    @SemanticIconMarker fun window_restore() = render(Name.window_restore)
    @SemanticIconMarker fun window_restore_outline() = render(Name.window_restore_outline)

    // Communication /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticIconMarker fun at() = render(Name.at)
    @SemanticIconMarker fun bell() = render(Name.bell)
    @SemanticIconMarker fun bell_outline() = render(Name.bell_outline)
    @SemanticIconMarker fun bell_slash() = render(Name.bell_slash)
    @SemanticIconMarker fun bell_slash_outline() = render(Name.bell_slash_outline)
    @SemanticIconMarker fun comment() = render(Name.comment)
    @SemanticIconMarker fun comment_outline() = render(Name.comment_outline)
    @SemanticIconMarker fun comment_alternate() = render(Name.comment_alternate)
    @SemanticIconMarker fun comment_alternate_outline() = render(Name.comment_alternate_outline)
    @SemanticIconMarker fun comments() = render(Name.comments)
    @SemanticIconMarker fun comments_outline() = render(Name.comments_outline)
    @SemanticIconMarker fun inbox() = render(Name.inbox)
    @SemanticIconMarker fun language() = render(Name.language)
    @SemanticIconMarker fun mobile() = render(Name.mobile)
    @SemanticIconMarker fun mobile_alternate() = render(Name.mobile_alternate)
    @SemanticIconMarker fun paper_plane() = render(Name.paper_plane)
    @SemanticIconMarker fun paper_plane_outline() = render(Name.paper_plane_outline)
    @SemanticIconMarker fun wifi() = render(Name.wifi)

    // Computers ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticIconMarker fun desktop() = render(Name.desktop)
    @SemanticIconMarker fun hdd() = render(Name.hdd)
    @SemanticIconMarker fun hdd_outline() = render(Name.hdd_outline)
    @SemanticIconMarker fun keyboard() = render(Name.keyboard)
    @SemanticIconMarker fun keyboard_outline() = render(Name.keyboard_outline)
    @SemanticIconMarker fun laptop() = render(Name.laptop)
    @SemanticIconMarker fun microchip() = render(Name.microchip)
    @SemanticIconMarker fun plug() = render(Name.plug)
    @SemanticIconMarker fun power_off() = render(Name.power_off)
    @SemanticIconMarker fun print() = render(Name.print)
    @SemanticIconMarker fun server() = render(Name.server)
    @SemanticIconMarker fun settings() = render(Name.settings)
    @SemanticIconMarker fun tablet() = render(Name.tablet)
    @SemanticIconMarker fun tablet_alternate() = render(Name.tablet_alternate)
    @SemanticIconMarker fun tv() = render(Name.tv)

    // Currency ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticIconMarker fun dollar_sign() = render(Name.dollar_sign)
    @SemanticIconMarker fun euro_sign() = render(Name.euro_sign)
    @SemanticIconMarker fun lira_sign() = render(Name.lira_sign)
    @SemanticIconMarker fun money_bill_alternate() = render(Name.money_bill_alternate)
    @SemanticIconMarker fun money_bill_alternate_outline() = render(Name.money_bill_alternate_outline)
    @SemanticIconMarker fun pound_sign() = render(Name.pound_sign)
    @SemanticIconMarker fun ruble_sign() = render(Name.ruble_sign)
    @SemanticIconMarker fun rupee_sign() = render(Name.rupee_sign)
    @SemanticIconMarker fun shekel_sign() = render(Name.shekel_sign)
    @SemanticIconMarker fun won_sign() = render(Name.won_sign)
    @SemanticIconMarker fun yen_sign() = render(Name.yen_sign)

    // Date & Time //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticIconMarker fun calendar_check() = render(Name.calendar_check)
    @SemanticIconMarker fun calendar_check_outline() = render(Name.calendar_check_outline)
    @SemanticIconMarker fun calendar_minus() = render(Name.calendar_minus)
    @SemanticIconMarker fun calendar_minus_outline() = render(Name.calendar_minus_outline)
    @SemanticIconMarker fun calendar_plus() = render(Name.calendar_plus)
    @SemanticIconMarker fun calendar_plus_outline() = render(Name.calendar_plus_outline)
    @SemanticIconMarker fun calendar_times() = render(Name.calendar_times)
    @SemanticIconMarker fun calendar_times_outline() = render(Name.calendar_times_outline)
    @SemanticIconMarker fun clock() = render(Name.clock)
    @SemanticIconMarker fun clock_outline() = render(Name.clock_outline)
    @SemanticIconMarker fun hourglass() = render(Name.hourglass)
    @SemanticIconMarker fun hourglass_outline() = render(Name.hourglass_outline)
    @SemanticIconMarker fun hourglass_end() = render(Name.hourglass_end)
    @SemanticIconMarker fun hourglass_half() = render(Name.hourglass_half)
    @SemanticIconMarker fun hourglass_start() = render(Name.hourglass_start)
    @SemanticIconMarker fun stopwatch() = render(Name.stopwatch)

    // Design ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticIconMarker fun adjust() = render(Name.adjust)
    @SemanticIconMarker fun clone() = render(Name.cloneIcon)
    @SemanticIconMarker fun clone_outline() = render(Name.clone_outline)
    @SemanticIconMarker fun crop() = render(Name.crop)
    @SemanticIconMarker fun crosshairs() = render(Name.crosshairs)
    @SemanticIconMarker fun dropdown() = render(Name.dropdown)
    @SemanticIconMarker fun eye() = render(Name.eye)
    @SemanticIconMarker fun eye_dropper() = render(Name.eye_dropper)
    @SemanticIconMarker fun eye_slash() = render(Name.eye_slash)
    @SemanticIconMarker fun eye_slash_outline() = render(Name.eye_slash_outline)
    @SemanticIconMarker fun object_group() = render(Name.object_group)
    @SemanticIconMarker fun object_group_outline() = render(Name.object_group_outline)
    @SemanticIconMarker fun object_ungroup() = render(Name.object_ungroup)
    @SemanticIconMarker fun object_ungroup_outline() = render(Name.object_ungroup_outline)
    @SemanticIconMarker fun paint_brush() = render(Name.paint_brush)
    @SemanticIconMarker fun tint() = render(Name.tint)

    // Editors //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticIconMarker fun align_center() = render(Name.align_center)
    @SemanticIconMarker fun align_justify() = render(Name.align_justify)
    @SemanticIconMarker fun align_left() = render(Name.align_left)
    @SemanticIconMarker fun align_right() = render(Name.align_right)
    @SemanticIconMarker fun bold() = render(Name.bold)
    @SemanticIconMarker fun font() = render(Name.font)
    @SemanticIconMarker fun heading() = render(Name.heading)
    @SemanticIconMarker fun i_cursor() = render(Name.i_cursor)
    @SemanticIconMarker fun indent() = render(Name.indent)
    @SemanticIconMarker fun italic() = render(Name.italic)
    @SemanticIconMarker fun linkify() = render(Name.linkify)
    @SemanticIconMarker fun list() = render(Name.list)
    @SemanticIconMarker fun list_alternate() = render(Name.list_alternate)
    @SemanticIconMarker fun list_alternate_outline() = render(Name.list_alternate_outline)
    @SemanticIconMarker fun list_ol() = render(Name.list_ol)
    @SemanticIconMarker fun list_ul() = render(Name.list_ul)
    @SemanticIconMarker fun outdent() = render(Name.outdent)
    @SemanticIconMarker fun paragraph() = render(Name.paragraph)
    @SemanticIconMarker fun quote_left() = render(Name.quote_left)
    @SemanticIconMarker fun quote_right() = render(Name.quote_right)
    @SemanticIconMarker fun strikethrough() = render(Name.strikethrough)
    @SemanticIconMarker fun subscript() = render(Name.subscript)
    @SemanticIconMarker fun superscript() = render(Name.superscript)
    @SemanticIconMarker fun th() = render(Name.th)
    @SemanticIconMarker fun th_large() = render(Name.th_large)
    @SemanticIconMarker fun th_list() = render(Name.th_list)
    @SemanticIconMarker fun trash() = render(Name.trash)
    @SemanticIconMarker fun trash_alternate() = render(Name.trash_alternate)
    @SemanticIconMarker fun trash_alternate_outline() = render(Name.trash_alternate_outline)
    @SemanticIconMarker fun underline() = render(Name.underline)
    @SemanticIconMarker fun unlink() = render(Name.unlink)

    // Files ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticIconMarker fun file_archive() = render(Name.file_archive)
    @SemanticIconMarker fun file_archive_outline() = render(Name.file_archive_outline)
    @SemanticIconMarker fun file_excel() = render(Name.file_excel)
    @SemanticIconMarker fun file_excel_outline() = render(Name.file_excel_outline)
    @SemanticIconMarker fun file_image() = render(Name.file_image)
    @SemanticIconMarker fun file_image_outline() = render(Name.file_image_outline)
    @SemanticIconMarker fun file_pdf() = render(Name.file_pdf)
    @SemanticIconMarker fun file_pdf_outline() = render(Name.file_pdf_outline)
    @SemanticIconMarker fun file_powerpoint() = render(Name.file_powerpoint)
    @SemanticIconMarker fun file_powerpoint_outline() = render(Name.file_powerpoint_outline)
    @SemanticIconMarker fun file_word() = render(Name.file_word)
    @SemanticIconMarker fun file_word_outline() = render(Name.file_word_outline)

    // Genders //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticIconMarker fun genderless() = render(Name.genderless)
    @SemanticIconMarker fun mars() = render(Name.mars)
    @SemanticIconMarker fun mars_double() = render(Name.mars_double)
    @SemanticIconMarker fun mars_stroke() = render(Name.mars_stroke)
    @SemanticIconMarker fun mars_stroke_horizontal() = render(Name.mars_stroke_horizontal)
    @SemanticIconMarker fun mars_stroke_vertical() = render(Name.mars_stroke_vertical)
    @SemanticIconMarker fun mercury() = render(Name.mercury)
    @SemanticIconMarker fun neuter() = render(Name.neuter)
    @SemanticIconMarker fun transgender() = render(Name.transgender)
    @SemanticIconMarker fun transgender_alternate() = render(Name.transgender_alternate)
    @SemanticIconMarker fun venus() = render(Name.venus)
    @SemanticIconMarker fun venus_double() = render(Name.venus_double)
    @SemanticIconMarker fun venus_mars() = render(Name.venus_mars)

    // Hands & Gestures /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticIconMarker fun hand_lizard() = render(Name.hand_lizard)
    @SemanticIconMarker fun hand_lizard_outline() = render(Name.hand_lizard_outline)
    @SemanticIconMarker fun hand_paper() = render(Name.hand_paper)
    @SemanticIconMarker fun hand_paper_outline() = render(Name.hand_paper_outline)
    @SemanticIconMarker fun hand_peace() = render(Name.hand_peace)
    @SemanticIconMarker fun hand_peace_outline() = render(Name.hand_peace_outline)
    @SemanticIconMarker fun hand_rock() = render(Name.hand_rock)
    @SemanticIconMarker fun hand_rock_outline() = render(Name.hand_rock_outline)
    @SemanticIconMarker fun hand_scissors() = render(Name.hand_scissors)
    @SemanticIconMarker fun hand_scissors_outline() = render(Name.hand_scissors_outline)
    @SemanticIconMarker fun hand_spock() = render(Name.hand_spock)
    @SemanticIconMarker fun hand_spock_outline() = render(Name.hand_spock_outline)
    @SemanticIconMarker fun handshake() = render(Name.handshake)
    @SemanticIconMarker fun handshake_outline() = render(Name.handshake_outline)
    @SemanticIconMarker fun thumbs_down() = render(Name.thumbs_down)
    @SemanticIconMarker fun thumbs_down_outline() = render(Name.thumbs_down_outline)
    @SemanticIconMarker fun thumbs_up() = render(Name.thumbs_up)
    @SemanticIconMarker fun thumbs_up_outline() = render(Name.thumbs_up_outline)

    // Health ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticIconMarker fun ambulance() = render(Name.ambulance)
    @SemanticIconMarker fun h_square() = render(Name.h_square)
    @SemanticIconMarker fun heart() = render(Name.heart)
    @SemanticIconMarker fun heart_outline() = render(Name.heart_outline)
    @SemanticIconMarker fun heartbeat() = render(Name.heartbeat)
    @SemanticIconMarker fun hospital() = render(Name.hospital)
    @SemanticIconMarker fun hospital_outline() = render(Name.hospital_outline)
    @SemanticIconMarker fun medkit() = render(Name.medkit)
    @SemanticIconMarker fun plus_square() = render(Name.plus_square)
    @SemanticIconMarker fun plus_square_outline() = render(Name.plus_square_outline)
    @SemanticIconMarker fun stethoscope() = render(Name.stethoscope)
    @SemanticIconMarker fun user_md() = render(Name.user_md)

    // Hotel ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticIconMarker fun door_open() = render("door open icon")

    // Images ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticIconMarker fun bolt() = render(Name.bolt)
    @SemanticIconMarker fun camera() = render(Name.camera)
    @SemanticIconMarker fun camera_retro() = render(Name.camera_retro)
    @SemanticIconMarker fun id_badge() = render(Name.id_badge)
    @SemanticIconMarker fun id_badge_outline() = render(Name.id_badge_outline)
    @SemanticIconMarker fun id_card() = render(Name.id_card)
    @SemanticIconMarker fun id_card_outline() = render(Name.id_card_outline)
    @SemanticIconMarker fun image() = render(Name.image)
    @SemanticIconMarker fun image_outline() = render(Name.image_outline)
    @SemanticIconMarker fun images() = render(Name.images)
    @SemanticIconMarker fun images_outline() = render(Name.images_outline)
    @SemanticIconMarker fun sliders_horizontal() = render(Name.sliders_horizontal)

    // Interfaces ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticIconMarker fun ban() = render(Name.ban)
    @SemanticIconMarker fun bars() = render(Name.bars)
    @SemanticIconMarker fun beer() = render(Name.beer)
    @SemanticIconMarker fun check() = render(Name.check)
    @SemanticIconMarker fun check_circle() = render(Name.check_circle)
    @SemanticIconMarker fun check_circle_outline() = render(Name.check_circle_outline)
    @SemanticIconMarker fun check_square() = render(Name.check_square)
    @SemanticIconMarker fun check_square_outline() = render(Name.check_square_outline)
    @SemanticIconMarker fun close() = render(Name.close)
    @SemanticIconMarker fun cloud() = render(Name.cloud)
    @SemanticIconMarker fun cog() = render(Name.cog)
    @SemanticIconMarker fun cogs() = render(Name.cogs)
    @SemanticIconMarker fun database() = render(Name.database)
    @SemanticIconMarker fun dot_circle() = render(Name.dot_circle)
    @SemanticIconMarker fun dot_circle_outline() = render(Name.dot_circle_outline)
    @SemanticIconMarker fun ellipsis_horizontal() = render(Name.ellipsis_horizontal)
    @SemanticIconMarker fun ellipsis_vertical() = render(Name.ellipsis_vertical)
    @SemanticIconMarker fun exclamation() = render(Name.exclamation)
    @SemanticIconMarker fun exclamation_circle() = render(Name.exclamation_circle)
    @SemanticIconMarker fun exclamation_triangle() = render(Name.exclamation_triangle)
    @SemanticIconMarker fun flag() = render(Name.flag)
    @SemanticIconMarker fun flag_outline() = render(Name.flag_outline)
    @SemanticIconMarker fun flag_checkered() = render(Name.flag_checkered)
    @SemanticIconMarker fun frown() = render(Name.frown)
    @SemanticIconMarker fun frown_outline() = render(Name.frown_outline)
    @SemanticIconMarker fun grip_horizontal() = render(Name.grip_horizontal)
    @SemanticIconMarker fun grip_lines() = render(Name.grip_lines)
    @SemanticIconMarker fun grip_lines_vertical() = render(Name.grip_lines_vertical)
    @SemanticIconMarker fun grip_vertical() = render(Name.grip_vertical)
    @SemanticIconMarker fun hashtag() = render(Name.hashtag)
    @SemanticIconMarker fun home() = render(Name.home)
    @SemanticIconMarker fun info() = render(Name.info)
    @SemanticIconMarker fun info_circle() = render(Name.info_circle)
    @SemanticIconMarker fun magic() = render(Name.magic)
    @SemanticIconMarker fun meh() = render(Name.meh)
    @SemanticIconMarker fun meh_outline() = render(Name.meh_outline)
    @SemanticIconMarker fun minus() = render(Name.minus)
    @SemanticIconMarker fun minus_circle() = render(Name.minus_circle)
    @SemanticIconMarker fun minus_square() = render(Name.minus_square)
    @SemanticIconMarker fun minus_square_outline() = render(Name.minus_square_outline)
    @SemanticIconMarker fun plus() = render(Name.plus)
    @SemanticIconMarker fun plus_circle() = render(Name.plus_circle)
    @SemanticIconMarker fun question() = render(Name.question)
    @SemanticIconMarker fun search() = render(Name.search)
    @SemanticIconMarker fun search_minus() = render(Name.search_minus)
    @SemanticIconMarker fun search_plus() = render(Name.search_plus)
    @SemanticIconMarker fun share_alternate() = render(Name.share_alternate)
    @SemanticIconMarker fun share_alternate_square() = render(Name.share_alternate_square)
    @SemanticIconMarker fun signal() = render(Name.signal)
    @SemanticIconMarker fun smile() = render(Name.smile)
    @SemanticIconMarker fun smile_outline() = render(Name.smile_outline)
    @SemanticIconMarker fun star() = render(Name.star)
    @SemanticIconMarker fun star_outline() = render(Name.star_outline)
    @SemanticIconMarker fun star_half() = render(Name.star_half)
    @SemanticIconMarker fun star_half_outline() = render(Name.star_half_outline)
    @SemanticIconMarker fun times() = render(Name.times)
    @SemanticIconMarker fun times_circle() = render(Name.times_circle)
    @SemanticIconMarker fun times_circle_outline() = render(Name.times_circle_outline)
    @SemanticIconMarker fun toggle_off() = render(Name.toggle_off)
    @SemanticIconMarker fun toggle_on() = render(Name.toggle_on)
    @SemanticIconMarker fun trophy() = render(Name.trophy)
    @SemanticIconMarker fun user() = render(Name.user)
    @SemanticIconMarker fun user_outline() = render(Name.user_outline)
    @SemanticIconMarker fun user_circle() = render(Name.user_circle)
    @SemanticIconMarker fun user_circle_outline() = render(Name.user_circle_outline)
    @SemanticIconMarker fun user_shield() = render(Name.user_shield)

    // Logistics ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticIconMarker fun box() = render(Name.box)
    @SemanticIconMarker fun boxes() = render(Name.boxes)
    @SemanticIconMarker fun clipboard_check() = render(Name.clipboard_check)
    @SemanticIconMarker fun clipboard_list() = render(Name.clipboard_list)
    @SemanticIconMarker fun dolly() = render(Name.dolly)
    @SemanticIconMarker fun dolly_flatbed() = render(Name.dolly_flatbed)
    @SemanticIconMarker fun pallet() = render(Name.pallet)
    @SemanticIconMarker fun shipping_fast() = render(Name.shipping_fast)
    @SemanticIconMarker fun truck() = render(Name.truck)
    @SemanticIconMarker fun warehouse() = render(Name.warehouse)

    // Maps /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticIconMarker fun anchor() = render(Name.anchor)
    @SemanticIconMarker fun bed() = render(Name.bed)
    @SemanticIconMarker fun bicycle() = render(Name.bicycle)
    @SemanticIconMarker fun binoculars() = render(Name.binoculars)
    @SemanticIconMarker fun bomb() = render(Name.bomb)
    @SemanticIconMarker fun bookmark() = render(Name.bookmark)
    @SemanticIconMarker fun bookmark_outline() = render(Name.bookmark_outline)
    @SemanticIconMarker fun car() = render(Name.car)
    @SemanticIconMarker fun fighter_jet() = render(Name.fighter_jet)
    @SemanticIconMarker fun fire() = render(Name.fire)
    @SemanticIconMarker fun flask() = render(Name.flask)
    @SemanticIconMarker fun gamepad() = render(Name.gamepad)
    @SemanticIconMarker fun gavel() = render(Name.gavel)
    @SemanticIconMarker fun gift() = render(Name.gift)
    @SemanticIconMarker fun glass_martini() = render(Name.glass_martini)
    @SemanticIconMarker fun graduation_cap() = render(Name.graduation_cap)
    @SemanticIconMarker fun key() = render(Name.key)
    @SemanticIconMarker fun leaf() = render(Name.leaf)
    @SemanticIconMarker fun lemon() = render(Name.lemon)
    @SemanticIconMarker fun lemon_outline() = render(Name.lemon_outline)
    @SemanticIconMarker fun life_ring() = render(Name.life_ring)
    @SemanticIconMarker fun life_ring_outline() = render(Name.life_ring_outline)
    @SemanticIconMarker fun lightbulb() = render(Name.lightbulb)
    @SemanticIconMarker fun lightbulb_outline() = render(Name.lightbulb_outline)
    @SemanticIconMarker fun magnet() = render(Name.magnet)
    @SemanticIconMarker fun male() = render(Name.male)
    @SemanticIconMarker fun map() = render(Name.map)
    @SemanticIconMarker fun map_outline() = render(Name.map_outline)
    @SemanticIconMarker fun map_marker() = render(Name.map_marker)
    @SemanticIconMarker fun map_marker_alternate() = render(Name.map_marker_alternate)
    @SemanticIconMarker fun map_pin() = render(Name.map_pin)
    @SemanticIconMarker fun map_signs() = render(Name.map_signs)
    @SemanticIconMarker fun motorcycle() = render(Name.motorcycle)
    @SemanticIconMarker fun newspaper() = render(Name.newspaper)
    @SemanticIconMarker fun newspaper_outline() = render(Name.newspaper_outline)
    @SemanticIconMarker fun paw() = render(Name.paw)
    @SemanticIconMarker fun plane() = render(Name.plane)
    @SemanticIconMarker fun road() = render(Name.road)
    @SemanticIconMarker fun rocket() = render(Name.rocket)
    @SemanticIconMarker fun ship() = render(Name.ship)
    @SemanticIconMarker fun shopping_bag() = render(Name.shopping_bag)
    @SemanticIconMarker fun shopping_basket() = render(Name.shopping_basket)
    @SemanticIconMarker fun shop() = render(Name.shop)
    @SemanticIconMarker fun shopping_cart() = render(Name.shopping_cart)
    @SemanticIconMarker fun shower() = render(Name.shower)
    @SemanticIconMarker fun street_view() = render(Name.street_view)
    @SemanticIconMarker fun subway() = render(Name.subway)
    @SemanticIconMarker fun taxi() = render(Name.taxi)
    @SemanticIconMarker fun ticket_alternate() = render(Name.ticket_alternate)
    @SemanticIconMarker fun train() = render(Name.train)
    @SemanticIconMarker fun tree() = render(Name.tree)
    @SemanticIconMarker fun umbrella() = render(Name.umbrella)
    @SemanticIconMarker fun university() = render(Name.university)
    @SemanticIconMarker fun utensil_spoon() = render(Name.utensil_spoon)
    @SemanticIconMarker fun utensils() = render(Name.utensils)
    @SemanticIconMarker fun wrench() = render(Name.wrench)

    // Medical //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticIconMarker fun band_aid() = render(Name.band_aid)
    @SemanticIconMarker fun dna() = render(Name.dna)
    @SemanticIconMarker fun first_aid() = render(Name.first_aid)
    @SemanticIconMarker fun hospital_symbol() = render(Name.hospital_symbol)
    @SemanticIconMarker fun pills() = render(Name.pills)
    @SemanticIconMarker fun syringe() = render(Name.syringe)
    @SemanticIconMarker fun thermometer() = render(Name.thermometer)
    @SemanticIconMarker fun weight() = render(Name.weight)

    // Objects //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticIconMarker fun bus() = render(Name.bus)
    @SemanticIconMarker fun cube() = render(Name.cube)
    @SemanticIconMarker fun cubes() = render(Name.cubes)
    @SemanticIconMarker fun futbol() = render(Name.futbol)
    @SemanticIconMarker fun futbol_outline() = render(Name.futbol_outline)
    @SemanticIconMarker fun gem() = render(Name.gem)
    @SemanticIconMarker fun gem_outline() = render(Name.gem_outline)
    @SemanticIconMarker fun lock() = render(Name.lock)
    @SemanticIconMarker fun lock_open() = render(Name.lock_open)
    @SemanticIconMarker fun moon() = render(Name.moon)
    @SemanticIconMarker fun moon_outline() = render(Name.moon_outline)
    @SemanticIconMarker fun puzzle_piece() = render(Name.puzzle_piece)
    @SemanticIconMarker fun snowflake() = render(Name.snowflake)
    @SemanticIconMarker fun snowflake_outline() = render(Name.snowflake_outline)
    @SemanticIconMarker fun space_shuttle() = render(Name.space_shuttle)
    @SemanticIconMarker fun sun() = render(Name.sun)
    @SemanticIconMarker fun sun_outline() = render(Name.sun_outline)
    @SemanticIconMarker fun tachometer_alternate() = render(Name.tachometer_alternate)
    @SemanticIconMarker fun unlock() = render(Name.unlock)
    @SemanticIconMarker fun unlock_alternate() = render(Name.unlock_alternate)

    // Payments & Shopping //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticIconMarker fun cart_plus() = render(Name.cart_plus)
    @SemanticIconMarker fun credit_card() = render(Name.credit_card)
    @SemanticIconMarker fun credit_card_outline() = render(Name.credit_card_outline)

    // Shapes ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticIconMarker fun square() = render(Name.square)
    @SemanticIconMarker fun square_outline() = render(Name.square_outline)

    // Spinners /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticIconMarker fun asterisk() = render(Name.asterisk)
    @SemanticIconMarker fun circle_notch() = render(Name.circle_notch)
    @SemanticIconMarker fun spinner() = render(Name.spinner)

    // Sports ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticIconMarker fun baseball_ball() = render(Name.baseball_ball)
    @SemanticIconMarker fun basketball_ball() = render(Name.basketball_ball)
    @SemanticIconMarker fun bowling_ball() = render(Name.bowling_ball)
    @SemanticIconMarker fun football_ball() = render(Name.football_ball)
    @SemanticIconMarker fun golf_ball() = render(Name.golf_ball)
    @SemanticIconMarker fun hockey_puck() = render(Name.hockey_puck)
    @SemanticIconMarker fun quidditch() = render(Name.quidditch)
    @SemanticIconMarker fun table_tennis() = render(Name.table_tennis)
    @SemanticIconMarker fun volleyball_ball() = render(Name.volleyball_ball)

    // Status ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticIconMarker fun battery_empty() = render(Name.battery_empty)
    @SemanticIconMarker fun battery_full() = render(Name.battery_full)
    @SemanticIconMarker fun battery_half() = render(Name.battery_half)
    @SemanticIconMarker fun battery_quarter() = render(Name.battery_quarter)
    @SemanticIconMarker fun battery_three_quarters() = render(Name.battery_three_quarters)
    @SemanticIconMarker fun thermometer_empty() = render(Name.thermometer_empty)
    @SemanticIconMarker fun thermometer_full() = render(Name.thermometer_full)
    @SemanticIconMarker fun thermometer_half() = render(Name.thermometer_half)
    @SemanticIconMarker fun thermometer_quarter() = render(Name.thermometer_quarter)
    @SemanticIconMarker fun thermometer_three_quarters() = render(Name.thermometer_three_quarters)

    // User & People ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticIconMarker fun child() = render(Name.child)
    @SemanticIconMarker fun female() = render(Name.female)
    @SemanticIconMarker fun user_plus() = render(Name.user_plus)
    @SemanticIconMarker fun user_times() = render(Name.user_times)
    @SemanticIconMarker fun users() = render(Name.users)

    // Brands ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticIconMarker fun _500px() = render(Name._500px)
    @SemanticIconMarker fun accessible_icon() = render(Name.accessible_icon)
    @SemanticIconMarker fun accusoft() = render(Name.accusoft)
    @SemanticIconMarker fun adn() = render(Name.adn)
    @SemanticIconMarker fun adversal() = render(Name.adversal)
    @SemanticIconMarker fun affiliatetheme() = render(Name.affiliatetheme)
    @SemanticIconMarker fun algolia() = render(Name.algolia)
    @SemanticIconMarker fun amazon() = render(Name.amazon)
    @SemanticIconMarker fun amazon_pay() = render(Name.amazon_pay)
    @SemanticIconMarker fun amilia() = render(Name.amilia)
    @SemanticIconMarker fun android() = render(Name.android)
    @SemanticIconMarker fun angellist() = render(Name.angellist)
    @SemanticIconMarker fun angrycreative() = render(Name.angrycreative)
    @SemanticIconMarker fun angular() = render(Name.angular)
    @SemanticIconMarker fun app_store() = render(Name.app_store)
    @SemanticIconMarker fun app_store_ios() = render(Name.app_store_ios)
    @SemanticIconMarker fun apper() = render(Name.apper)
    @SemanticIconMarker fun apple() = render(Name.apple)
    @SemanticIconMarker fun apple_pay() = render(Name.apple_pay)
    @SemanticIconMarker fun asymmetrik() = render(Name.asymmetrik)
    @SemanticIconMarker fun audible() = render(Name.audible)
    @SemanticIconMarker fun autoprefixer() = render(Name.autoprefixer)
    @SemanticIconMarker fun avianex() = render(Name.avianex)
    @SemanticIconMarker fun aviato() = render(Name.aviato)
    @SemanticIconMarker fun aws() = render(Name.aws)
    @SemanticIconMarker fun bandcamp() = render(Name.bandcamp)
    @SemanticIconMarker fun behance() = render(Name.behance)
    @SemanticIconMarker fun behance_square() = render(Name.behance_square)
    @SemanticIconMarker fun bimobject() = render(Name.bimobject)
    @SemanticIconMarker fun bitbucket() = render(Name.bitbucket)
    @SemanticIconMarker fun bitcoin() = render(Name.bitcoin)
    @SemanticIconMarker fun bity() = render(Name.bity)
    @SemanticIconMarker fun black_tie() = render(Name.black_tie)
    @SemanticIconMarker fun blackberry() = render(Name.blackberry)
    @SemanticIconMarker fun blogger() = render(Name.blogger)
    @SemanticIconMarker fun blogger_b() = render(Name.blogger_b)
    @SemanticIconMarker fun bluetooth() = render(Name.bluetooth)
    @SemanticIconMarker fun bluetooth_b() = render(Name.bluetooth_b)
    @SemanticIconMarker fun btc() = render(Name.btc)
    @SemanticIconMarker fun buromobelexperte() = render(Name.buromobelexperte)
    @SemanticIconMarker fun buysellads() = render(Name.buysellads)
    @SemanticIconMarker fun cc_amazon_pay() = render(Name.cc_amazon_pay)
    @SemanticIconMarker fun cc_amex() = render(Name.cc_amex)
    @SemanticIconMarker fun cc_apple_pay() = render(Name.cc_apple_pay)
    @SemanticIconMarker fun cc_diners_club() = render(Name.cc_diners_club)
    @SemanticIconMarker fun cc_discover() = render(Name.cc_discover)
    @SemanticIconMarker fun cc_jcb() = render(Name.cc_jcb)
    @SemanticIconMarker fun cc_mastercard() = render(Name.cc_mastercard)
    @SemanticIconMarker fun cc_paypal() = render(Name.cc_paypal)
    @SemanticIconMarker fun cc_stripe() = render(Name.cc_stripe)
    @SemanticIconMarker fun cc_visa() = render(Name.cc_visa)
    @SemanticIconMarker fun centercode() = render(Name.centercode)
    @SemanticIconMarker fun chrome() = render(Name.chrome)
    @SemanticIconMarker fun cloudscale() = render(Name.cloudscale)
    @SemanticIconMarker fun cloudsmith() = render(Name.cloudsmith)
    @SemanticIconMarker fun cloudversify() = render(Name.cloudversify)
    @SemanticIconMarker fun codepen() = render(Name.codepen)
    @SemanticIconMarker fun codiepie() = render(Name.codiepie)
    @SemanticIconMarker fun connectdevelop() = render(Name.connectdevelop)
    @SemanticIconMarker fun contao() = render(Name.contao)
    @SemanticIconMarker fun cpanel() = render(Name.cpanel)
    @SemanticIconMarker fun creative_commons() = render(Name.creative_commons)
    @SemanticIconMarker fun css3() = render(Name.css3)
    @SemanticIconMarker fun css3_alternate() = render(Name.css3_alternate)
    @SemanticIconMarker fun cuttlefish() = render(Name.cuttlefish)
    @SemanticIconMarker fun d_and_d() = render(Name.d_and_d)
    @SemanticIconMarker fun dashcube() = render(Name.dashcube)
    @SemanticIconMarker fun delicious() = render(Name.delicious)
    @SemanticIconMarker fun deploydog() = render(Name.deploydog)
    @SemanticIconMarker fun deskpro() = render(Name.deskpro)
    @SemanticIconMarker fun deviantart() = render(Name.deviantart)
    @SemanticIconMarker fun digg() = render(Name.digg)
    @SemanticIconMarker fun digital_ocean() = render(Name.digital_ocean)
    @SemanticIconMarker fun discord() = render(Name.discord)
    @SemanticIconMarker fun discourse() = render(Name.discourse)
    @SemanticIconMarker fun dochub() = render(Name.dochub)
    @SemanticIconMarker fun docker() = render(Name.docker)
    @SemanticIconMarker fun draft2digital() = render(Name.draft2digital)
    @SemanticIconMarker fun dribbble() = render(Name.dribbble)
    @SemanticIconMarker fun dribbble_square() = render(Name.dribbble_square)
    @SemanticIconMarker fun dropbox() = render(Name.dropbox)
    @SemanticIconMarker fun drupal() = render(Name.drupal)
    @SemanticIconMarker fun dyalog() = render(Name.dyalog)
    @SemanticIconMarker fun earlybirds() = render(Name.earlybirds)
    @SemanticIconMarker fun edge() = render(Name.edge)
    @SemanticIconMarker fun elementor() = render(Name.elementor)
    @SemanticIconMarker fun ember() = render(Name.ember)
    @SemanticIconMarker fun empire() = render(Name.empire)
    @SemanticIconMarker fun envira() = render(Name.envira)
    @SemanticIconMarker fun erlang() = render(Name.erlang)
    @SemanticIconMarker fun ethereum() = render(Name.ethereum)
    @SemanticIconMarker fun etsy() = render(Name.etsy)
    @SemanticIconMarker fun expeditedssl() = render(Name.expeditedssl)
    @SemanticIconMarker fun facebook() = render(Name.facebook)
    @SemanticIconMarker fun facebook_f() = render(Name.facebook_f)
    @SemanticIconMarker fun facebook_messenger() = render(Name.facebook_messenger)
    @SemanticIconMarker fun facebook_square() = render(Name.facebook_square)
    @SemanticIconMarker fun firefox() = render(Name.firefox)
    @SemanticIconMarker fun first_order() = render(Name.first_order)
    @SemanticIconMarker fun firstdraft() = render(Name.firstdraft)
    @SemanticIconMarker fun flickr() = render(Name.flickr)
    @SemanticIconMarker fun flipboard() = render(Name.flipboard)
    @SemanticIconMarker fun fly() = render(Name.fly)
    @SemanticIconMarker fun font_awesome() = render(Name.font_awesome)
    @SemanticIconMarker fun font_awesome_alternate() = render(Name.font_awesome_alternate)
    @SemanticIconMarker fun font_awesome_flag() = render(Name.font_awesome_flag)
    @SemanticIconMarker fun fonticons() = render(Name.fonticons)
    @SemanticIconMarker fun fonticons_fi() = render(Name.fonticons_fi)
    @SemanticIconMarker fun fort_awesome() = render(Name.fort_awesome)
    @SemanticIconMarker fun fort_awesome_alternate() = render(Name.fort_awesome_alternate)
    @SemanticIconMarker fun forumbee() = render(Name.forumbee)
    @SemanticIconMarker fun foursquare() = render(Name.foursquare)
    @SemanticIconMarker fun free_code_camp() = render(Name.free_code_camp)
    @SemanticIconMarker fun freebsd() = render(Name.freebsd)
    @SemanticIconMarker fun get_pocket() = render(Name.get_pocket)
    @SemanticIconMarker fun gg() = render(Name.gg)
    @SemanticIconMarker fun gg_circle() = render(Name.gg_circle)
    @SemanticIconMarker fun git() = render(Name.git)
    @SemanticIconMarker fun git_square() = render(Name.git_square)
    @SemanticIconMarker fun github() = render(Name.github)
    @SemanticIconMarker fun github_alternate() = render(Name.github_alternate)
    @SemanticIconMarker fun github_square() = render(Name.github_square)
    @SemanticIconMarker fun gitkraken() = render(Name.gitkraken)
    @SemanticIconMarker fun gitlab() = render(Name.gitlab)
    @SemanticIconMarker fun gitter() = render(Name.gitter)
    @SemanticIconMarker fun glide() = render(Name.glide)
    @SemanticIconMarker fun glide_g() = render(Name.glide_g)
    @SemanticIconMarker fun gofore() = render(Name.gofore)
    @SemanticIconMarker fun goodreads() = render(Name.goodreads)
    @SemanticIconMarker fun goodreads_g() = render(Name.goodreads_g)
    @SemanticIconMarker fun google() = render(Name.google)
    @SemanticIconMarker fun google_drive() = render(Name.google_drive)
    @SemanticIconMarker fun google_play() = render(Name.google_play)
    @SemanticIconMarker fun google_plus() = render(Name.google_plus)
    @SemanticIconMarker fun google_plus_g() = render(Name.google_plus_g)
    @SemanticIconMarker fun google_plus_square() = render(Name.google_plus_square)
    @SemanticIconMarker fun google_wallet() = render(Name.google_wallet)
    @SemanticIconMarker fun gratipay() = render(Name.gratipay)
    @SemanticIconMarker fun grav() = render(Name.grav)
    @SemanticIconMarker fun gripfire() = render(Name.gripfire)
    @SemanticIconMarker fun grunt() = render(Name.grunt)
    @SemanticIconMarker fun gulp() = render(Name.gulp)
    @SemanticIconMarker fun hacker_news() = render(Name.hacker_news)
    @SemanticIconMarker fun hacker_news_square() = render(Name.hacker_news_square)
    @SemanticIconMarker fun hips() = render(Name.hips)
    @SemanticIconMarker fun hire_a_helper() = render(Name.hire_a_helper)
    @SemanticIconMarker fun hooli() = render(Name.hooli)
    @SemanticIconMarker fun hotjar() = render(Name.hotjar)
    @SemanticIconMarker fun houzz() = render(Name.houzz)
    @SemanticIconMarker fun html5() = render(Name.html5)
    @SemanticIconMarker fun hubspot() = render(Name.hubspot)
    @SemanticIconMarker fun imdb() = render(Name.imdb)
    @SemanticIconMarker fun instagram() = render(Name.instagram)
    @SemanticIconMarker fun internet_explorer() = render(Name.internet_explorer)
    @SemanticIconMarker fun ioxhost() = render(Name.ioxhost)
    @SemanticIconMarker fun itunes() = render(Name.itunes)
    @SemanticIconMarker fun itunes_note() = render(Name.itunes_note)
    @SemanticIconMarker fun jenkins() = render(Name.jenkins)
    @SemanticIconMarker fun joget() = render(Name.joget)
    @SemanticIconMarker fun joomla() = render(Name.joomla)
    @SemanticIconMarker fun js() = render(Name.js)
    @SemanticIconMarker fun js_square() = render(Name.js_square)
    @SemanticIconMarker fun jsfiddle() = render(Name.jsfiddle)
    @SemanticIconMarker fun keycdn() = render(Name.keycdn)
    @SemanticIconMarker fun kickstarter() = render(Name.kickstarter)
    @SemanticIconMarker fun kickstarter_k() = render(Name.kickstarter_k)
    @SemanticIconMarker fun korvue() = render(Name.korvue)
    @SemanticIconMarker fun laravel() = render(Name.laravel)
    @SemanticIconMarker fun lastfm() = render(Name.lastfm)
    @SemanticIconMarker fun lastfm_square() = render(Name.lastfm_square)
    @SemanticIconMarker fun leanpub() = render(Name.leanpub)
    @SemanticIconMarker fun less() = render(Name.less)
    @SemanticIconMarker fun linechat() = render(Name.linechat)
    @SemanticIconMarker fun linkedin() = render(Name.linkedin)
    @SemanticIconMarker fun linkedin_in() = render(Name.linkedin_in)
    @SemanticIconMarker fun linode() = render(Name.linode)
    @SemanticIconMarker fun linux() = render(Name.linux)
    @SemanticIconMarker fun lyft() = render(Name.lyft)
    @SemanticIconMarker fun magento() = render(Name.magento)
    @SemanticIconMarker fun maxcdn() = render(Name.maxcdn)
    @SemanticIconMarker fun medapps() = render(Name.medapps)
    @SemanticIconMarker fun medium_() = render(Name.medium)
    @SemanticIconMarker fun medium_m() = render(Name.medium_m)
    @SemanticIconMarker fun medrt() = render(Name.medrt)
    @SemanticIconMarker fun meetup() = render(Name.meetup)
    @SemanticIconMarker fun microsoft() = render(Name.microsoft)
    @SemanticIconMarker fun mix() = render(Name.mix)
    @SemanticIconMarker fun mixcloud() = render(Name.mixcloud)
    @SemanticIconMarker fun mizuni() = render(Name.mizuni)
    @SemanticIconMarker fun modx() = render(Name.modx)
    @SemanticIconMarker fun monero() = render(Name.monero)
    @SemanticIconMarker fun napster() = render(Name.napster)
    @SemanticIconMarker fun nintendo_switch() = render(Name.nintendo_switch)
    @SemanticIconMarker fun node() = render(Name.node)
    @SemanticIconMarker fun node_js() = render(Name.node_js)
    @SemanticIconMarker fun npm() = render(Name.npm)
    @SemanticIconMarker fun ns8() = render(Name.ns8)
    @SemanticIconMarker fun nutritionix() = render(Name.nutritionix)
    @SemanticIconMarker fun odnoklassniki() = render(Name.odnoklassniki)
    @SemanticIconMarker fun odnoklassniki_square() = render(Name.odnoklassniki_square)
    @SemanticIconMarker fun opencart() = render(Name.opencart)
    @SemanticIconMarker fun openid() = render(Name.openid)
    @SemanticIconMarker fun opera() = render(Name.opera)
    @SemanticIconMarker fun optin_monster() = render(Name.optin_monster)
    @SemanticIconMarker fun osi() = render(Name.osi)
    @SemanticIconMarker fun page4() = render(Name.page4)
    @SemanticIconMarker fun pagelines() = render(Name.pagelines)
    @SemanticIconMarker fun palfed() = render(Name.palfed)
    @SemanticIconMarker fun patreon() = render(Name.patreon)
    @SemanticIconMarker fun paypal() = render(Name.paypal)
    @SemanticIconMarker fun periscope() = render(Name.periscope)
    @SemanticIconMarker fun phabricator() = render(Name.phabricator)
    @SemanticIconMarker fun phoenix_framework() = render(Name.phoenix_framework)
    @SemanticIconMarker fun php() = render(Name.php)
    @SemanticIconMarker fun pied_piper() = render(Name.pied_piper)
    @SemanticIconMarker fun pied_piper_alternate() = render(Name.pied_piper_alternate)
    @SemanticIconMarker fun pied_piper_pp() = render(Name.pied_piper_pp)
    @SemanticIconMarker fun pinterest() = render(Name.pinterest)
    @SemanticIconMarker fun pinterest_p() = render(Name.pinterest_p)
    @SemanticIconMarker fun pinterest_square() = render(Name.pinterest_square)
    @SemanticIconMarker fun playstation() = render(Name.playstation)
    @SemanticIconMarker fun product_hunt() = render(Name.product_hunt)
    @SemanticIconMarker fun pushed() = render(Name.pushed)
    @SemanticIconMarker fun python() = render(Name.python)
    @SemanticIconMarker fun qq() = render(Name.qq)
    @SemanticIconMarker fun quinscape() = render(Name.quinscape)
    @SemanticIconMarker fun quora() = render(Name.quora)
    @SemanticIconMarker fun ravelry() = render(Name.ravelry)
    @SemanticIconMarker fun react() = render(Name.react)
    @SemanticIconMarker fun rebel() = render(Name.rebel)
    @SemanticIconMarker fun redriver() = render(Name.redriver)
    @SemanticIconMarker fun reddit() = render(Name.reddit)
    @SemanticIconMarker fun reddit_alien() = render(Name.reddit_alien)
    @SemanticIconMarker fun reddit_square() = render(Name.reddit_square)
    @SemanticIconMarker fun rendact() = render(Name.rendact)
    @SemanticIconMarker fun renren() = render(Name.renren)
    @SemanticIconMarker fun replyd() = render(Name.replyd)
    @SemanticIconMarker fun resolving() = render(Name.resolving)
    @SemanticIconMarker fun rocketchat() = render(Name.rocketchat)
    @SemanticIconMarker fun rockrms() = render(Name.rockrms)
    @SemanticIconMarker fun safari() = render(Name.safari)
    @SemanticIconMarker fun sass() = render(Name.sass)
    @SemanticIconMarker fun schlix() = render(Name.schlix)
    @SemanticIconMarker fun scribd() = render(Name.scribd)
    @SemanticIconMarker fun searchengin() = render(Name.searchengin)
    @SemanticIconMarker fun sellcast() = render(Name.sellcast)
    @SemanticIconMarker fun sellsy() = render(Name.sellsy)
    @SemanticIconMarker fun servicestack() = render(Name.servicestack)
    @SemanticIconMarker fun shirtsinbulk() = render(Name.shirtsinbulk)
    @SemanticIconMarker fun simplybuilt() = render(Name.simplybuilt)
    @SemanticIconMarker fun sistrix() = render(Name.sistrix)
    @SemanticIconMarker fun skyatlas() = render(Name.skyatlas)
    @SemanticIconMarker fun skype() = render(Name.skype)
    @SemanticIconMarker fun slack() = render(Name.slack)
    @SemanticIconMarker fun slack_hash() = render(Name.slack_hash)
    @SemanticIconMarker fun slideshare() = render(Name.slideshare)
    @SemanticIconMarker fun snapchat() = render(Name.snapchat)
    @SemanticIconMarker fun snapchat_ghost() = render(Name.snapchat_ghost)
    @SemanticIconMarker fun snapchat_square() = render(Name.snapchat_square)
    @SemanticIconMarker fun soundcloud() = render(Name.soundcloud)
    @SemanticIconMarker fun speakap() = render(Name.speakap)
    @SemanticIconMarker fun spotify() = render(Name.spotify)
    @SemanticIconMarker fun stack_exchange() = render(Name.stack_exchange)
    @SemanticIconMarker fun stack_overflow() = render(Name.stack_overflow)
    @SemanticIconMarker fun staylinked() = render(Name.staylinked)
    @SemanticIconMarker fun steam() = render(Name.steam)
    @SemanticIconMarker fun steam_square() = render(Name.steam_square)
    @SemanticIconMarker fun steam_symbol() = render(Name.steam_symbol)
    @SemanticIconMarker fun sticker_mule() = render(Name.sticker_mule)
    @SemanticIconMarker fun strava() = render(Name.strava)
    @SemanticIconMarker fun stripe() = render(Name.stripe)
    @SemanticIconMarker fun stripe_s() = render(Name.stripe_s)
    @SemanticIconMarker fun studiovinari() = render(Name.studiovinari)
    @SemanticIconMarker fun stumbleupon() = render(Name.stumbleupon)
    @SemanticIconMarker fun stumbleupon_circle() = render(Name.stumbleupon_circle)
    @SemanticIconMarker fun superpowers() = render(Name.superpowers)
    @SemanticIconMarker fun supple() = render(Name.supple)
    @SemanticIconMarker fun telegram() = render(Name.telegram)
    @SemanticIconMarker fun telegram_plane() = render(Name.telegram_plane)
    @SemanticIconMarker fun tencent_weibo() = render(Name.tencent_weibo)
    @SemanticIconMarker fun themeisle() = render(Name.themeisle)
    @SemanticIconMarker fun trello() = render(Name.trello)
    @SemanticIconMarker fun tripadvisor() = render(Name.tripadvisor)
    @SemanticIconMarker fun tumblr() = render(Name.tumblr)
    @SemanticIconMarker fun tumblr_square() = render(Name.tumblr_square)
    @SemanticIconMarker fun twitch() = render(Name.twitch)
    @SemanticIconMarker fun twitter() = render(Name.twitter)
    @SemanticIconMarker fun twitter_square() = render(Name.twitter_square)
    @SemanticIconMarker fun typo3() = render(Name.typo3)
    @SemanticIconMarker fun uber() = render(Name.uber)
    @SemanticIconMarker fun uikit() = render(Name.uikit)
    @SemanticIconMarker fun uniregistry() = render(Name.uniregistry)
    @SemanticIconMarker fun untappd() = render(Name.untappd)
    @SemanticIconMarker fun usb() = render(Name.usb)
    @SemanticIconMarker fun ussunnah() = render(Name.ussunnah)
    @SemanticIconMarker fun vaadin() = render(Name.vaadin)
    @SemanticIconMarker fun viacoin() = render(Name.viacoin)
    @SemanticIconMarker fun viadeo() = render(Name.viadeo)
    @SemanticIconMarker fun viadeo_square() = render(Name.viadeo_square)
    @SemanticIconMarker fun viber() = render(Name.viber)
    @SemanticIconMarker fun vimeo() = render(Name.vimeo)
    @SemanticIconMarker fun vimeo_square() = render(Name.vimeo_square)
    @SemanticIconMarker fun vimeo_v() = render(Name.vimeo_v)
    @SemanticIconMarker fun vine() = render(Name.vine)
    @SemanticIconMarker fun vk() = render(Name.vk)
    @SemanticIconMarker fun vnv() = render(Name.vnv)
    @SemanticIconMarker fun vuejs() = render(Name.vuejs)
    @SemanticIconMarker fun wechat() = render(Name.wechat)
    @SemanticIconMarker fun weibo() = render(Name.weibo)
    @SemanticIconMarker fun weixin() = render(Name.weixin)
    @SemanticIconMarker fun whatsapp() = render(Name.whatsapp)
    @SemanticIconMarker fun whatsapp_square() = render(Name.whatsapp_square)
    @SemanticIconMarker fun whmcs() = render(Name.whmcs)
    @SemanticIconMarker fun wikipedia_w() = render(Name.wikipedia_w)
    @SemanticIconMarker fun windows() = render(Name.windows)
    @SemanticIconMarker fun wordpress() = render(Name.wordpress)
    @SemanticIconMarker fun wordpress_simple() = render(Name.wordpress_simple)
    @SemanticIconMarker fun wpbeginner() = render(Name.wpbeginner)
    @SemanticIconMarker fun wpexplorer() = render(Name.wpexplorer)
    @SemanticIconMarker fun wpforms() = render(Name.wpforms)
    @SemanticIconMarker fun xbox() = render(Name.xbox)
    @SemanticIconMarker fun xing() = render(Name.xing)
    @SemanticIconMarker fun xing_square() = render(Name.xing_square)
    @SemanticIconMarker fun y_combinator() = render(Name.y_combinator)
    @SemanticIconMarker fun yahoo() = render(Name.yahoo)
    @SemanticIconMarker fun yandex() = render(Name.yandex)
    @SemanticIconMarker fun yandex_international() = render(Name.yandex_international)
    @SemanticIconMarker fun yelp() = render(Name.yelp)
    @SemanticIconMarker fun yoast() = render(Name.yoast)
    @SemanticIconMarker fun youtube() = render(Name.youtube)
    @SemanticIconMarker fun youtube_square() = render(Name.youtube_square)
}
