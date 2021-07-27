import Vue from "vue";
import Vuex from "vuex";
import jwt_decode from "jwt-decode";
import { findById } from "@/api/user.js";

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        isLogin: false, // 로그인 여부
        memberInfo: null,
        feeds: [],
        comments: [],
        whole_challenges: [],
        team_challenges: [],
        team: [],
        teamInfo: null,
        myTeamList:[],
    },
  
        

    getters: {
        team_challenges(state) {
            return state.team_challenges;
        },
        whole_challenges(state) {
            return state.whole_challenges;
        },
        myTeamList(state) {
            return state.myTeamList;
        },
        feeds(state) {
            return state.feeds;
        },
        comments(state) {
            return state.comments;
        },
    },
    mutations: {
        setIsLogined(state, isLogin) {
            state.isLogin = isLogin;
        },
        setMemberInfo(state, memberInfo) {
            state.isLogin = true;
            state.memberInfo = memberInfo;
        },
        logout(state) {
            state.isLogin = false;
            state.memberInfo = null;
        },
        setFeeds(state, payload) {
            state.feeds = payload;
        },
        setComments(state, payload) {
            state.comments = payload;
        },
        SET_WHOLECHALLENGE(state, whole_projects) {
            state.whole_projects = whole_projects;
        },
        SET_TEAMCHALLENGE(state, data) {
            state.team_challenges.length = 0;
            console.log(data);
            data.forEach(element => {
                state.team_challenges.push({ value: element, text: element });
            });
        },
        SET_WHOLETEAMCHALLENGE(state, data) {
            state.whole_challenges.length = 0;
            console.log(data);
            data.forEach(element => {
                state.whole_challenges.push({ value: element, text: element });
            });
        },
        SET_MY_TEAMLIST(state, data) {
            state.myTeamList.length = 0;
            console.log(data);
            data.forEach(element => {
                state.myTeamList.push({ value: element, text: element });
            });
        },
    },
    actions: {
        async GET_MEMBER_INFO({ commit }, token) {
            let decode = jwt_decode(token);
                console.log(decode);
            await findById(
            decode.memberEmail,
            (response) => {
                if (response.data.message === "success") {
                commit("setMemberInfo", response.data.memberInfo);
                // router.push("/");
                // router.go(router.currentRoute);
                } else {
                    console.log("유저 정보 없음!!");
                }
            },
            (error) => {
                console.log(error);
            }
        );
        },
        LOGOUT({ commit }) {
            commit("logout");
            localStorage.removeItem("access-token");
        },

        // async GET_TEAMCHALLENGE_INFO({ commit }) {
        //     let decode = jwt_decode(token);
        //         console.log(decode);
        //     await findById(
        //         decode.memberEmail,
        //         (response) => {
        //             if (response.data.message === "success") {
        //             commit("setMemberInfo", response.data.memberInfo);
        //             // router.push("/");
        //             // router.go(router.currentRoute);
        //             } else {
        //                 console.log("유저 정보 없음!!");
        //             }
        //         },
        //         (error) => {
        //             console.log(error);
        //         }
        //     );
        // },

        async GET_TEAMCHALLENGE_INFO({ commit }) {
            await http
                .get("/challenge/team_challenge_list")
                .then((data) => {
                    console.log(data.data);
                    commit("SET_TEAMCHALLENGE", data.data);
                })
                .catch(() => {
                    alert("에러발생!");
                });
        },

        async GET_WHOLECHALLENGE_INFO({ commit }) {
            await http
                .get("/challenge/whole_challenge_list")
                .then((data) => {
                    console.log(data.data);
                    commit("SET_WHOLETEAMCHALLENGE", data.data);
                })
                .catch(() => {
                    alert("에러발생!");
                });
        },

        async GET_TEAM_INFO({ commit }) {
            await http
                .get("/team/my_team_list")
                .then((data) => {
                    console.log(data.data);
                    commit("SET_MY_TEAMLIST", data.data);
                })
                .catch(() => {
                    alert("에러발생!");
                });
        },
        getFeeds(context) {
            http
                .get("")
                .then(({ data }) => {
                    context.commit("setFeeds", data);
                })
                .catch(() => {
                    alert("에러발생");
                });
        },
        getComments(context) {
                http
                .get("")
                .then(({ data }) => {
                    context.commit("setComments", data);
                })
                .catch(() => {
                    alert("에러발생");
                });
        }

    }
});
