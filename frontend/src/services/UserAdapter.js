import User from "@/models/User";

/**
 * @Author: Dylan Weijgertze
 */
export default class UserAdapter {
    RESOURCE_URL;

    constructor(resourceUrl) {
        this.RESOURCE_URL = resourceUrl;
    }

    /**
     * Update the general user information
     *
     * @param {User} user
     * @return {Promise<User|null>}
     */
    async updateUserInfo(user) {
        let updatedUser = await fetch(
            `${this.RESOURCE_URL}`,
            {
                method: 'PUT',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(user),
                credentials: 'include'
            }
        );

        if (updatedUser.ok) {
            updatedUser = await updatedUser.json();
            localStorage.setItem("user", JSON.stringify(updatedUser));
            return User.createUserFromJson(updatedUser);
        } else {
            return null;
        }
    }

    /**
     * Update the password of the logged-in user
     *
     * @param {string} oldPassword
     * @param {string} newPassword
     * @return {Promise<Response<any, Record<string, any>, number>>}
     */
    async updateUserPassword(oldPassword, newPassword) {
        return await fetch(
            `${this.RESOURCE_URL}/password`,
            {
                method: 'PUT',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({oldPassword, newPassword}),
                credentials: 'include'
            }
        );
    }

    async updateNotificationSettings(preferences) {
        return await fetch(
            `${this.RESOURCE_URL}/notification-preferences`,
            {
                method: 'PUT',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(preferences),
                credentials: 'include'
            }
        );
    }

    /**
     * Get the notification settings for the user with the given id
     *
     * @returns The notification settings
     */
    async findNotificationSettings() {
        const settings = await fetch(`${process.env.VUE_APP_API_URL}/notification-settings`)
            .then(res => res.json());
        const preferences = await fetch(`${this.RESOURCE_URL}/notification-preferences`)
            .then(res => res.json());

        // convert settings to map
        const settingsMap = {};
        settings?.forEach(setting => {
            settingsMap[setting.id] = setting
            settingsMap[setting.id].notificationPreferenceId = undefined;
            settingsMap[setting.id].isEmailActive = false;
            settingsMap[setting.id].isPushActive = false;
        });

        // add preferences to map
        preferences?.forEach(preference => {
            settingsMap[preference.notificationSetting.id].notificationPreferenceId = preference.id;
            settingsMap[preference.notificationSetting.id].isEmailActive = preference.emailActive;
            settingsMap[preference.notificationSetting.id].isPushActive = preference.pushActive;
        });

        return settingsMap;
    }
}