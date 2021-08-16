<template>
  <div>
    <v-layout justify-start align-center data-aos="fade-up">
      <v-toolbar-title class="headline">
          <span><b>팀</b></span>
          <span class="green--text"><b>&nbsp;가입신청</b></span>
      </v-toolbar-title>
      <br><br><br><br>
    </v-layout>
    <!-- <table v-if="waitingReqests.length !== 0">
        <tr v-for="request in waitingReqests" v-bind:key="request.requestId" >
            <td>{{request.member.name}}님의 가입요청</td>
            <td><b-button variant="primary" v-on:click="acceptRequest(request.requestId, request.member.memberId)">수락</b-button></td>
            <td><b-button variant="danger" v-on:click="rejectRequest(request.requestId)">거절</b-button></td>
        </tr>
    </table> -->
    <!-- <table v-else>
      <tr><b>
        가입 요청이 존재하지 않습니다.
      </b></tr>
    </table> -->
    
    <template v-if="waitingReqests.length !== 0">
      <v-container style="padding-bottom: 0px;" fluid grid-list-md>
        <v-data-iterator
          :items="this.waitingReqests"
          :rows-per-page-items="rowsPerPageItems"
          :pagination.sync="pagination"
          :search="search"
          content-tag="v-layout"
          hide-default-footer
          row
          wrap
        >
          <template v-slot:header>
            <v-toolbar
              dark
              color="blue darken-3"
              class="mb-1"
            >
              <v-text-field
                v-model="search"
                clearable
                flat
                solo-inverted
                hide-details
                prepend-inner-icon="mdi-magnify"
                label="이름 검색"
              ></v-text-field>
            </v-toolbar>
          </template>
          <template v-slot:item="props">
            <v-flex
              xs12
              sm6
              md4
              lg3
            >
              <v-card>
                <v-card-title class="align-center justify-center subheading font-weight-bold" style="padding-bottom: 0px">
                  {{ props.item.member.name }} 님
                </v-card-title>
                <v-divider></v-divider>
                <v-list dense>
                  <v-list-tile>
                    <v-list-tile-content class="align-center">
                      <b-button variant="primary" v-on:click="acceptRequest(props.item.requestId, props.item.member.memberId)">가입요청 수락</b-button>
                    </v-list-tile-content>
                  </v-list-tile>

                  <v-list-tile>
                    <v-list-tile-content class="align-center">
                      <b-button variant="danger" v-on:click="rejectRequest(props.item.requestId)">가입요청 거절</b-button>
                    </v-list-tile-content>
                  </v-list-tile>
                </v-list>
              </v-card>
            </v-flex>
          </template>
          
        </v-data-iterator>
      </v-container>
    </template>

    <template v-else>
      <b>
        가입 요청이 존재하지 않습니다.
      </b>
    </template>

  </div>
</template>

<script>
import { mapState } from "vuex";
// import http from "@/util/http-common";
import { createInstance } from "@/api/teamindex.js";

export default {
  name: "JoinRequest",
  data() {
    return {
      search: '',
      rowsPerPageItems: [4, 8, 12],
      pagination: {
        rowsPerPage: 4
      },
      items: [
        {
          name: 'member.name',
        },
      ],
    }
  },
  computed: {
    waitingReqests: function() {
      return this.joinRequests.filter(i => i.status.includes("WAITING"));
    },
    ...mapState(["managingTeam", "joinRequests", "memberInfo"]),
    numberOfPages () {
      return Math.ceil(this.items.length / this.itemsPerPage)
    },
    filteredKeys () {
      return this.keys.filter(key => key !== 'Name')
    },
  },
  mounted() {
    this.$nextTick(function() {
      // this.$store.dispatch('getRequests', this.managingTeam.teamId);
    });
  },
  methods: {
    rejectRequest: function(requestId) {
      const instance = createInstance();
      instance
        .put("/request/reject/" + requestId)
        .then(() =>
          this.$store.dispatch("getRequests", this.managingTeam.teamId)
        );
    },
    acceptRequest: function(requestId, memberId) {
      const instance = createInstance();
      instance
        .put("/request/accept/" + requestId)
        .then(() =>
          this.$store.dispatch("getRequests", this.managingTeam.teamId)
        );
        alert("요청이 승인되었습니다. 잠시 뒤 반영됩니다.");
      let joinTeam = {
        member: {
          memberId: this.memberInfo.memberId
        },
        team: {
          teamId: this.managingTeam.teamId
        }
      };
    },
    nextPage () {
      if (this.page + 1 <= this.numberOfPages) this.page += 1
    },
    formerPage () {
      if (this.page - 1 >= 1) this.page -= 1
    },
    updateItemsPerPage (number) {
      this.itemsPerPage = number
    },
  }
};
</script>

<style scope>

</style>
