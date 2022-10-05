/*! Nestoria Slider - v1.0.13 - 2015-07-16
* http://lokku.github.io/jquery-nstslider/
* Copyright (c) 2015 Lokku Ltd.; Licensed MIT */
!function (a) {
    var b, c, d, e, f, g, h, i, j, k = {
        setNakedBarDelta: function (a, b) {
            if ("stickToSides" === a) j = {toEndWidth: b, toBeginLeft: 0, toBeginWidth: b}; else {
                if ("middle" !== a) throw new Error("unknown position of setNakedBarDelta: " + a);
                j = {toEndWidth: b / 2, toBeginLeft: b / 2, toBeginWidth: b / 2}
            }
        }, getSliderValuesAtPositionPx: function (a, b) {
            var c, d, e = this, f = e.data("pixel_to_value_mapping");
            if ("undefined" != typeof f) c = f(a), d = f(b); else {
                var g = k.getSliderWidthPx.call(e) - e.data("left_grip_width");
                c = k.inverse_rangemap_0_to_n.call(e, a, g), d = k.inverse_rangemap_0_to_n.call(e, b, g)
            }
            return [c, d]
        }, validateAndMoveGripsToPx: function (a, b) {
            var c = this, d = k.getSliderWidthPx.call(c) - c.data("left_grip_width");
            if (d >= b && a >= 0 && d >= a && (!c.data("has_right_grip") || b >= a)) {
                var e = c.data("cur_min"), f = c.data("cur_max");
                k.set_position_from_px.call(c, a, b), k.refresh_grips_style.call(c), k.notify_changed_implicit.call(c, "drag_move", e, f)
            }
            return c
        }, updateAriaAttributes: function () {
            var a = this, b = a.data("settings"), c = a.find(b.left_grip_selector);
            if (a.data("has_right_grip")) {
                var d = a.find(b.right_grip_selector);
                c.attr("aria-valuemin", a.data("range_min")).attr("aria-valuenow", l.get_current_min_value.call(a)).attr("aria-valuemax", l.get_current_max_value.call(a)), d.attr("aria-valuemin", l.get_current_min_value.call(a)).attr("aria-valuenow", l.get_current_max_value.call(a)).attr("aria-valuemax", a.data("range_max"))
            } else c.attr("aria-valuemin", a.data("range_min")).attr("aria-valuenow", l.get_current_min_value.call(a)).attr("aria-valuemax", a.data("range_max"));
            return a
        }, getSliderWidthPx: function () {
            var a = this;
            return Math.round(a.width())
        }, getGripPositionPx: function (a) {
            return parseInt(a.css("left").replace("px", ""), 10)
        }, getLeftGripPositionPx: function () {
            var a = this, b = a.data("settings"), c = a.find(b.left_grip_selector);
            return k.getGripPositionPx.call(a, c)
        }, getRightGripPositionPx: function () {
            var a = this, b = a.data("settings");
            if (a.data("has_right_grip")) return k.getGripPositionPx.call(a, a.find(b.right_grip_selector));
            var c = k.getSliderWidthPx.call(a) - a.data("left_grip_width");
            return k.rangemap_0_to_n.call(a, a.data("cur_max"), c)
        }, getLeftGripWidth: function () {
            var a = this, b = a.data("settings"), c = a.find(b.left_grip_selector);
            return Math.round(c.outerWidth())
        }, getRightGripWidth: function () {
            var a = this, b = a.data("settings"), c = a.find(b.right_grip_selector);
            return Math.round(c.outerWidth())
        }, binarySearchValueToPxCompareFunc: function (b, c, d) {
            return b === c[d] ? 0 : b < c[d] && 0 === d ? 0 : c[d - 1] <= b && b < c[d] ? 0 : b > c[d] ? 1 : b <= c[d - 1] ? -1 : void a.error("cannot compare s: " + b + " with a[" + d + "]. a is: " + c.join(","))
        }, binarySearch: function (a, b, c, d) {
            for (var e, f, g = 0, h = a.length - 1; h >= g;) {
                e = (g + h) / 2 | 0, f = c(a, e);
                var i = d(b, a, e);
                if (i > 0) g = e + 1; else {
                    if (!(0 > i)) return e;
                    h = e - 1
                }
            }
            return -1
        }, haveLimits: function () {
            var a = this, b = a.data("lower-limit"), c = a.data("upper-limit"), d = !1;
            return "undefined" != typeof b && "undefined" != typeof c && (d = !0), d
        }, refresh_grips_style: function () {
            var a = this, b = a.data("settings");
            if ("undefined" != typeof b.highlight) {
                var c = a.data("highlightedRangeMin");
                if ("undefined" != typeof c) {
                    var d = a.find(b.left_grip_selector), e = a.find(b.right_grip_selector),
                        f = a.data("highlightedRangeMax"), g = a.data("cur_min"), h = a.data("cur_max"),
                        i = b.highlight.grip_class;
                    c > g || g > f ? d.removeClass(i) : d.addClass(i), c > h || h > f ? e.removeClass(i) : e.addClass(i)
                }
            }
        }, set_position_from_val: function (a, b) {
            var c = this, d = c.data("range_min"), e = c.data("range_max");
            d > a && (a = d), a > e && (a = e), c.data("has_right_grip") ? (b > e && (b = e), d > b && (b = d)) : b = c.data("cur_max");
            var f = l.value_to_px.call(c, a), g = l.value_to_px.call(c, b);
            return k.set_handles_at_px.call(c, f, g), c.data("cur_min", a), c.data("has_right_grip") && c.data("cur_max", b), c
        }, set_position_from_px: function (a, b) {
            var c = this;
            k.set_handles_at_px.call(c, a, b);
            var d = k.getSliderValuesAtPositionPx.call(c, a, b), e = d[0], f = d[1];
            return c.data("cur_min", e), c.data("has_right_grip") && c.data("cur_max", f), c
        }, set_handles_at_px: function (a, b) {
            var c = this, d = c.data("settings"), e = d.left_grip_selector, f = d.right_grip_selector,
                g = d.value_bar_selector, h = c.data("left_grip_width");
            return c.find(e).css("left", a + "px"), c.find(f).css("left", b + "px"), c.data("has_right_grip") ? c.find(g).css("left", a + "px").css("width", b - a + h + "px") : (j || k.populateNakedBarDeltas.call(c, a, b, h), b > a ? c.find(g).css("left", a + "px").css("width", b - a + j.toEndWidth + "px") : c.find(g).css("left", b + j.toBeginLeft + "px").css("width", a - b + j.toBeginWidth + "px")), c
        }, drag_start_func_touch: function (a, b, c, e, f) {
            var g = this, h = a.originalEvent, i = h.touches[0], j = i.pageY, l = i.pageX,
                m = Math.abs(g.offset().top - j), n = g.offset().left, o = n - l, p = l - (n + g.width());
            m > b.touch_tolerance_value_bar_y || o > b.touch_tolerance_value_bar_x || p > b.touch_tolerance_value_bar_x || (h.preventDefault(), d = i.pageX, k.drag_start_func.call(g, i, b, c, e, f))
        }, drag_start_func: function (d, f, g, h, i) {
            var j = this;
            if (j.find(f.left_grip_selector + "," + f.value_bar_selector + "," + f.right_grip_selector).removeClass(f.animating_css_class), l.is_enabled.call(j)) {
                var m = a(d.target), n = !1;
                if ("object" == typeof f.highlight && (n = m.is(f.highlight.panel_selector)), i !== !1 || m.is(f.left_grip_selector) || m.is(f.right_grip_selector) || m.is(f.value_bar_selector) || n || m.is(j)) {
                    b = j;
                    var o, p, q, r, s, t, u = k.getGripPositionPx.call(j, g),
                        v = k.getSliderWidthPx.call(j) - j.data("left_grip_width"), w = g.offset().left,
                        x = k.getRightGripPositionPx.call(j);
                    p = Math.round(d.pageX) - j.data("left_grip_width") / 2, q = Math.abs(w - p), s = p - w, j.data("has_right_grip") ? (o = h.offset().left, r = Math.abs(o - p), t = p - o) : (r = 2 * q, t = 2 * s), f.user_drag_start_callback.call(j, d), q === r ? w > p ? (u += s, e = !0) : (x += t, e = !1) : r > q ? (u += s, e = !0) : (x += t, e = !1), j.data("has_right_grip") ? x > v && (x = v) : u > v && (u = v), 0 > u && (u = 0), c = !0;
                    var y = j.data("cur_min"), z = j.data("cur_max");
                    k.set_position_from_px.call(j, u, x), k.refresh_grips_style.call(j), k.notify_changed_implicit.call(j, "drag_start", y, z), "[object Touch]" !== Object.prototype.toString.apply(d) && d.preventDefault()
                }
            }
        }, drag_move_func_touch: function (a) {
            if (c === !0) {
                var b = a.originalEvent;
                b.preventDefault();
                var d = b.touches[0];
                k.drag_move_func(d)
            }
        }, drag_move_func: function (a) {
            if (c) {
                var f = b, g = f.data("settings"), h = k.getSliderWidthPx.call(f) - f.data("left_grip_width"),
                    i = k.getLeftGripPositionPx.call(f), j = k.getRightGripPositionPx.call(f), l = Math.round(a.pageX),
                    m = l - d, n = f.data("left_grip_width") / 2, o = f.offset().left + f.data("left_grip_width") - n,
                    p = o + h;
                g.crossable_handles === !1 && f.data("has_right_grip") && (e ? p = o + j : o += i);
                var q = 0, r = 0;
                o > l && (q = 1, r = 0), l > p && (r = 1, q = 0), g.crossable_handles === !0 && f.data("has_right_grip") && (e ? h >= j && i + m > j && (e = !1, i = j) : i >= 0 && i > j + m && (e = !0, j = i));
                var s = i, t = j;
                (m > 0 && !q || 0 > m && !r) && (e ? s += m : t += m), k.validateAndMoveGripsToPx.call(f, s, t), d = l, "[object Touch]" !== Object.prototype.toString.apply(a) && a.preventDefault()
            }
        }, drag_end_func_touch: function (a) {
            var b = a.originalEvent;
            b.preventDefault();
            var c = b.touches[0];
            k.drag_end_func(c)
        }, drag_end_func: function () {
            var a = b;
            if ("undefined" != typeof a) {
                c = !1, d = void 0, k.notify_mouse_up_implicit.call(a, e), b = void 0;
                var f = a.data("settings");
                a.find(f.left_grip_selector + "," + f.value_bar_selector + "," + f.right_grip_selector).addClass(f.animating_css_class)
            }
        }, get_rounding_for_value: function (a) {
            var b = this, c = b.data("rounding"), d = b.data("rounding_ranges");
            if ("object" == typeof d) {
                var e = k.binarySearch.call(b, d, a, function (a, b) {
                    return a[b].range
                }, function (a, b, c) {
                    return a < b[c].range ? c > 0 ? a >= b[c - 1].range ? 0 : -1 : 0 : 1
                });
                if (c = 1, e > -1) c = parseInt(d[e].value, 10); else {
                    var f = d.length - 1;
                    a >= d[f].range && (c = d[f].value)
                }
            }
            return c
        }, notify_mouse_up_implicit: function (a) {
            var b = this, c = l.get_current_min_value.call(b), d = l.get_current_max_value.call(b), e = !1;
            (b.data("beforestart_min") !== c || b.data("beforestart_max") !== d) && (e = !0, b.data("beforestart_min", c), b.data("beforestart_max", d));
            var f = b.data("settings");
            return f.user_mouseup_callback.call(b, l.get_current_min_value.call(b), l.get_current_max_value.call(b), a, e), b
        }, notify_changed_implicit: function (a, b, c) {
            var d = this, e = !1;
            ("init" === a || "refresh" === a) && (e = !0);
            var f = l.get_current_min_value.call(d), g = l.get_current_max_value.call(d);
            return e || (b = l.round_value_according_to_rounding.call(d, b), c = l.round_value_according_to_rounding.call(d, c)), (e || f !== b || g !== c) && (k.notify_changed_explicit.call(d, a, b, c, f, g), e = 1), e
        }, notify_changed_explicit: function (a, b, c, d, e) {
            var f = this, g = f.data("settings");
            return f.data("aria_enabled") && k.updateAriaAttributes.call(f), g.value_changed_callback.call(f, a, d, e, b, c), f
        }, validate_params: function (b) {
            var c = this, d = c.data("range_min"), e = c.data("range_max"), f = c.data("cur_min"),
                g = c.data("lower-limit"), h = c.data("upper-limit"), i = k.haveLimits.call(c);
            "undefined" == typeof d && a.error("the data-range_min attribute was not defined"), "undefined" == typeof e && a.error("the data-range_max attribute was not defined"), "undefined" == typeof f && a.error("the data-cur_min attribute must be defined"), d > e && a.error("Invalid input parameter. must be min < max"), i && g > h && a.error("Invalid data-lower-limit or data-upper-limit"), 0 === c.find(b.left_grip_selector).length && a.error("Cannot find element pointed by left_grip_selector: " + b.left_grip_selector), "undefined" != typeof b.right_grip_selector && 0 === c.find(b.right_grip_selector).length && a.error("Cannot find element pointed by right_grip_selector: " + b.right_grip_selector), "undefined" != typeof b.value_bar_selector && 0 === c.find(b.value_bar_selector).length && a.error("Cannot find element pointed by value_bar_selector" + b.value_bar_selector)
        }, rangemap_0_to_n: function (a, b) {
            var c = this, d = c.data("range_min"), e = c.data("range_max");
            return d >= a ? 0 : a >= e ? b : Math.floor((b * a - b * d) / (e - d))
        }, inverse_rangemap_0_to_n: function (a, b) {
            var c = this, d = c.data("range_min"), e = c.data("range_max");
            if (0 >= a) return d;
            if (a >= b) return e;
            var f = (e - d) * a / b;
            return f + d
        }
    }, l = {
        teardown: function () {
            var b = this;
            return b.removeData(), a(document).unbind("mousemove.nstSlider").unbind("mouseup.nstSlider"), b.parent().unbind("mousedown.nstSlider").unbind("touchstart.nstSlider").unbind("touchmove.nstSlider").unbind("touchend.nstSlider"), b.unbind("keydown.nstSlider").unbind("keyup.nstSlider"), b
        }, init: function (b) {
            var c = a.extend({
                animating_css_class: "nst-animating",
                touch_tolerance_value_bar_y: 30,
                touch_tolerance_value_bar_x: 15,
                left_grip_selector: ".nst-slider-grip-left",
                right_grip_selector: void 0,
                highlight: void 0,
                rounding: void 0,
                value_bar_selector: void 0,
                crossable_handles: !0,
                value_changed_callback: function () {
                },
                user_mouseup_callback: function () {
                },
                user_drag_start_callback: function () {
                }
            }, b), d = a(document);
            return d.unbind("mouseup.nstSlider"), d.unbind("mousemove.nstSlider"), d.bind("mousemove.nstSlider", k.drag_move_func), d.bind("mouseup.nstSlider", k.drag_end_func), this.each(function () {
                var b = a(this), d = b.parent();
                b.data("enabled", !0);
                var j = b.data("range_min"), m = b.data("range_max"), n = b.data("cur_min"), o = b.data("cur_max");
                "undefined" == typeof o && (o = n), "" === j && (j = 0), "" === m && (m = 0), "" === n && (n = 0), "" === o && (o = 0), b.data("range_min", j), b.data("range_max", m), b.data("cur_min", n), b.data("cur_max", o), k.validate_params.call(b, c), b.data("settings", c), "undefined" != typeof c.rounding ? l.set_rounding.call(b, c.rounding) : "undefined" != typeof b.data("rounding") ? l.set_rounding.call(b, b.data("rounding")) : l.set_rounding.call(b, 1);
                var p = b.find(c.left_grip_selector)[0], q = a(p), r = a(b.find(c.right_grip_selector)[0]);
                "undefined" == typeof q.attr("tabindex") && q.attr("tabindex", 0);
                var s = !1;
                b.find(c.right_grip_selector).length > 0 && (s = !0, "undefined" == typeof r.attr("tabindex") && r.attr("tabindex", 0)), b.data("has_right_grip", s), b.data("aria_enabled") === !0 && (q.attr("role", "slider").attr("aria-disabled", "false"), s && r.attr("role", "slider").attr("aria-disabled", "false")), b.bind("keyup.nstSlider", function (a) {
                    if (b.data("enabled")) {
                        switch (a.which) {
                            case 37:
                            case 38:
                            case 39:
                            case 40:
                                if (f === h) {
                                    var c, d, j, m = k.getSliderWidthPx.call(b);
                                    if (0 > g - i) {
                                        for (d = i; m >= d; d++) if (c = l.round_value_according_to_rounding.call(b, k.getSliderValuesAtPositionPx.call(b, d, d)[1]), c !== h) {
                                            j = d;
                                            break
                                        }
                                    } else for (d = i; d >= 0; d--) if (c = l.round_value_according_to_rounding.call(b, k.getSliderValuesAtPositionPx.call(b, d, d)[1]), c !== h) {
                                        j = d;
                                        break
                                    }
                                    e ? k.validateAndMoveGripsToPx.call(b, j, k.getRightGripPositionPx.call(b)) : k.validateAndMoveGripsToPx.call(b, k.getLeftGripPositionPx.call(b), j), k.notify_mouse_up_implicit.call(b, e)
                                }
                        }
                        f = void 0, g = void 0, h = void 0, i = void 0
                    }
                }), b.bind("keydown.nstSlider", function (a) {
                    if (b.data("enabled")) {
                        var c = function (a, c) {
                            var d = k.getLeftGripPositionPx.call(b), j = k.getRightGripPositionPx.call(b);
                            switch ("undefined" == typeof f && (g = e ? d : j, f = e ? l.get_current_min_value.call(b) : l.get_current_max_value.call(b)), c.which) {
                                case 37:
                                case 40:
                                    e ? d-- : j--, c.preventDefault();
                                    break;
                                case 38:
                                case 39:
                                    e ? d++ : j++, c.preventDefault()
                            }
                            i = e ? d : j, k.validateAndMoveGripsToPx.call(b, d, j), h = e ? l.get_current_min_value.call(b) : l.get_current_max_value.call(b)
                        };
                        s && b.find(":focus").is(r) ? (e = !1, c.call(b, r, a)) : (e = !0, c.call(b, q, a))
                    }
                });
                var t = k.getLeftGripWidth.call(b), u = s ? k.getRightGripWidth.call(b) : t;
                if (b.data("left_grip_width", t), b.data("right_grip_width", u), b.data("value_bar_selector", c.value_bar_selector), !s) {
                    var v = o === m || o === j;
                    k.setNakedBarDelta.call(b, v ? "stickToSides" : "middle", t)
                }
                j === m || n === o ? l.set_range.call(b, j, m) : k.set_position_from_val.call(b, b.data("cur_min"), b.data("cur_max")), k.notify_changed_implicit.call(b, "init"), b.data("beforestart_min", l.get_current_min_value.call(b)), b.data("beforestart_max", l.get_current_max_value.call(b)), b.bind("mousedown.nstSlider", function (a) {
                    k.drag_start_func.call(b, a, c, q, r, !1)
                }), d.bind("touchstart.nstSlider", function (a) {
                    k.drag_start_func_touch.call(b, a, c, q, r, !0)
                }), d.bind("touchend.nstSlider", function (a) {
                    k.drag_end_func_touch.call(b, a)
                }), d.bind("touchmove.nstSlider", function (a) {
                    k.drag_move_func_touch.call(b, a)
                });
                var w = b.data("histogram");
                "undefined" != typeof w && l.set_step_histogram.call(b, w)
            })
        }, get_range_min: function () {
            var a = this;
            return a.data("range_min")
        }, get_range_max: function () {
            var a = this;
            return a.data("range_max")
        }, get_current_min_value: function () {
            var b, c = a(this), d = l.get_range_min.call(c), e = l.get_range_max.call(c), f = c.data("cur_min");
            if (b = d >= f ? d : l.round_value_according_to_rounding.call(c, f), k.haveLimits.call(c)) {
                if (d >= b) return c.data("lower-limit");
                if (b >= e) return c.data("upper-limit")
            } else {
                if (d >= b) return d;
                if (b >= e) return e
            }
            return b
        }, get_current_max_value: function () {
            var b, c = a(this), d = l.get_range_min.call(c), e = l.get_range_max.call(c), f = c.data("cur_max");
            if (b = f >= e ? e : l.round_value_according_to_rounding.call(c, f), k.haveLimits.call(c)) {
                if (b >= e) return c.data("upper-limit");
                if (d >= b) return c.data("lower-limit")
            } else {
                if (b >= e) return e;
                if (d >= b) return d
            }
            return b
        }, is_handle_to_left_extreme: function () {
            var a = this;
            return k.haveLimits.call(a) ? a.data("lower-limit") === l.get_current_min_value.call(a) : l.get_range_min.call(a) === l.get_current_min_value.call(a)
        }, is_handle_to_right_extreme: function () {
            var a = this;
            return k.haveLimits.call(a) ? a.data("upper-limit") === l.get_current_max_value.call(a) : l.get_range_max.call(a) === l.get_current_max_value.call(a)
        }, refresh: function () {
            var a = this, b = a.data("last_step_histogram");
            "undefined" != typeof b && l.set_step_histogram.call(a, b), k.set_position_from_val.call(a, l.get_current_min_value.call(a), l.get_current_max_value.call(a));
            var c = a.data("highlightedRangeMin");
            if ("number" == typeof c) {
                var d = a.data("highlightedRangeMax");
                l.highlight_range.call(a, c, d)
            }
            return k.notify_changed_implicit.call(a, "refresh"), a
        }, disable: function () {
            var a = this, b = a.data("settings");
            return a.data("enabled", !1).find(b.left_grip_selector).attr("aria-disabled", "true").end().find(b.right_grip_selector).attr("aria-disabled", "true"), a
        }, enable: function () {
            var a = this, b = a.data("settings");
            return a.data("enabled", !0).find(b.left_grip_selector).attr("aria-disabled", "false").end().find(b.right_grip_selector).attr("aria-disabled", "false"), a
        }, is_enabled: function () {
            var a = this;
            return a.data("enabled")
        }, set_position: function (a, b) {
            var c = this, d = c.data("cur_min"), e = c.data("cur_max");
            a > b ? k.set_position_from_val.call(c, b, a) : k.set_position_from_val.call(c, a, b), k.refresh_grips_style.call(c), k.notify_changed_implicit.call(c, "set_position", d, e), c.data("beforestart_min", a), c.data("beforestart_max", b)
        }, set_step_histogram: function (b) {
            var c = this;
            c.data("last_step_histogram", b), "undefined" == typeof b && (a.error("got an undefined histogram in set_step_histogram"), k.unset_step_histogram.call(c));
            var d = k.getSliderWidthPx.call(c) - c.data("left_grip_width"), e = b.length;
            if (!(0 >= d)) {
                var f, g = 0;
                for (f = 0; e > f; f++) g += b[f];
                if (0 === g) return l.unset_step_histogram.call(c), c;
                var h = parseFloat(g) / d;
                for (f = 0; e > f; f++) b[f] = b[f] / h;
                var i = [b[0]];
                for (f = 1; e > f; f++) {
                    var j = i[f - 1] + b[f];
                    i.push(j)
                }
                i.push(d);
                for (var m = [c.data("range_min")], n = 0, o = m[0], p = 0; d >= n;) {
                    var q = parseInt(i.shift(), 10), r = k.inverse_rangemap_0_to_n.call(c, p + 1, e + 1);
                    p++;
                    var s = q - n, t = r - o;
                    for (f = n; q > f; f++) {
                        var u = o + t * (f - n + 1) / s;
                        m.push(u), n++, o = u
                    }
                    if (n === d) break
                }
                m[m.length - 1] = c.data("range_max");
                var v = function (a) {
                    return m[parseInt(a, 10)]
                }, w = function (a) {
                    var b = k.binarySearch.call(c, m, a, function (a, b) {
                        return a[b]
                    }, k.binarySearchValueToPxCompareFunc);
                    return m[b] === a ? b : Math.abs(m[b - 1] - a) < Math.abs(m[b] - a) ? b - 1 : b
                };
                return c.data("pixel_to_value_mapping", v), c.data("value_to_pixel_mapping", w), c
            }
        }, unset_step_histogram: function () {
            var a = this;
            return a.removeData("pixel_to_value_mapping"), a.removeData("value_to_pixel_mapping"), a.removeData("last_step_histogram"), a
        }, set_range: function (a, b) {
            var c = this, d = l.get_current_min_value.call(c), e = l.get_current_max_value.call(c);
            return c.data("range_min", a), c.data("range_max", b), k.set_position_from_val.call(c, d, e), k.notify_changed_implicit.call(c, "set_range", d, e), c
        }, highlight_range: function (b, c) {
            var d = this, e = d.data("settings");
            "undefined" == typeof e.highlight && a.error('you cannot call highlight_range if you haven\' specified the "highlight" parameter in construction!'), b || (b = 0), c || (c = 0);
            var f = l.value_to_px.call(d, b), g = l.value_to_px.call(d, c), h = g - f + d.data("left_grip_width"),
                i = d.find(e.highlight.panel_selector);
            return i.css("left", f + "px"), i.css("width", h + "px"), d.data("highlightedRangeMin", b), d.data("highlightedRangeMax", c), k.refresh_grips_style.call(d), d
        }, set_rounding: function (b) {
            var c = this;
            "string" == typeof b && b.indexOf("{") > -1 && (b = a.parseJSON(b)), c.data("rounding", b);
            var d = [];
            if ("object" == typeof b) {
                var e;
                for (e in b) if (b.hasOwnProperty(e)) {
                    var f = b[e];
                    d.push({range: f, value: e})
                }
                d.sort(function (a, b) {
                    return a.range - b.range
                }), c.data("rounding_ranges", d)
            } else c.removeData("rounding_ranges");
            return c
        }, get_rounding: function () {
            var a = this;
            return a.data("rounding")
        }, round_value_according_to_rounding: function (b) {
            var c = this, d = k.get_rounding_for_value.call(c, b);
            if (d > 0) {
                var e = b / d, f = parseInt(e, 10), g = e - f;
                g > .5 && f++;
                var h = f * d;
                return h
            }
            return a.error("rounding must be > 0, got " + d + " instead"), b
        }, value_to_px: function (a) {
            var b = this, c = b.data("value_to_pixel_mapping");
            if ("undefined" != typeof c) return c(a);
            var d = k.getSliderWidthPx.call(b) - b.data("left_grip_width");
            return k.rangemap_0_to_n.call(b, a, d)
        }
    }, m = "nstSlider";
    a.fn[m] = function (b) {
        if (l[b]) {
            if (this.data("initialized") === !0) return l[b].apply(this, Array.prototype.slice.call(arguments, 1));
            throw new Error("method " + b + " called on an uninitialized instance of " + m)
        }
        return "object" != typeof b && b ? void a.error("Cannot call method " + b) : (this.data("initialized", !0), l.init.apply(this, arguments))
    }
}(jQuery);


$('.nstSlider').nstSlider({
    "crossable_handles": false,
    "left_grip_selector": ".leftGrip",
    "right_grip_selector": ".rightGrip",
    "value_bar_selector": ".bar",
    "value_changed_callback": function (cause, leftValue, rightValue) {
        $(this).parent().find('.leftLabel').text(leftValue);
        $(this).parent().find('.rightLabel').text(rightValue);
    }
});