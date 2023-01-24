import { mount } from '@vue/test-utils'
import NotificationsDetail from "@/components/dashboard/notification/NotificationsDetail";

describe('NotificationsDetail', () => {
    it('should display the correct notification type', () => {
        const wrapper = mount(NotificationsDetail, {
            propsData: {
                selectedNotification: {
                    title: 'Test title',
                    body: 'Test body',
                    date: new Date(),
                    notificationType: 'info'
                }
            }
        })

        expect(wrapper.text()).toContain('Info')
    })
})
