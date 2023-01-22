import {mount} from '@vue/test-utils'
import UserInfoForm from "./UserInfoForm.vue";
import UserAdapterInMemory from "@/services/UserAdapter-InMemory";
import SessionSbServiceInMemory from "@/services/SessionSbService-InMemory";
import {reactive} from "vue";
import User from "@/models/User";

/**
 * @Author: Dylan Weijgertze
 */

let wrapper;
let currentUser;

beforeEach(async function() {
    let userAdapterInMemory = new UserAdapterInMemory(10000);
    currentUser = userAdapterInMemory.users[0];
    let sessionSbServiceInMemory = new SessionSbServiceInMemory(currentUser);

    wrapper = await mount(UserInfoForm, {
        global: {
            provide: {
                "userService": reactive(userAdapterInMemory),
                "sessionService": reactive(sessionSbServiceInMemory)
            }
        }
    });
});

it('should load in user', () => {
    const firstNameInputValue = wrapper.find("input[name=firstName]").element.value;

    expect(firstNameInputValue,
        'current users first name did not load in')
        .toBe(currentUser.firstName);

    expect(wrapper.vm.$data.user.equals(currentUser),
        'User\'s first name did not load into the user info form')
        .toBeTruthy();
});

it('submit should be disabled when nothing changed', async () => {
    await wrapper.find("input[name=firstName]").setValue(currentUser.firstName);
    const isSubmitDisabled = wrapper.find("button").element.disabled;

    expect(isSubmitDisabled,
        'submit should be disabled when user info didn\'t change')
        .toBeTruthy();
});

it('submit should be enabled when something changed', async () => {
    await wrapper.find("input[name=firstName]").setValue('Mick');
    const isSubmitDisabled = wrapper.find("button").element.disabled;

    expect(isSubmitDisabled,
        'submit should be enabled when user info did change')
        .toBeFalsy();
});

it('should update a users info',  async () => {
    const newName = 'Mickey';
    const newUserInfo = User.copyUser(currentUser);
    newUserInfo.firstName = newName;

    // change and submit user info form
    await wrapper.find("input[name=firstName]").setValue(newName);
    await wrapper.find("form").trigger('submit.prevent');

    // see if toast is emitted with success
    expect(wrapper.emitted('showToast')[0][0],
        'form toast did not happen')
        .toBe('success');

    // see if user changed
    expect(wrapper.vm.$data.user.equals(newUserInfo),
        'user is not updated correctly')
        .toBeTruthy();
});