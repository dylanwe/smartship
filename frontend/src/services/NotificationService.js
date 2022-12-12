import { Service, Autowired } from "vue-class-component";
import Notification from "@/models/notifications/Notification";
import * as NotificationRepository from "backend/src/main/java/com/smartship/backend/app/repositories/NotificationRepository.java";

@Service
export default class NotificationService {
    @Autowired
    private repository: NotificationRepository;

    public findByMessageContainsLetters(letters: string): Notification[] {
        return this.repository.findByMessageContainsLetters(letters);
    }

    public findAllInOrderFromNewestToOldest(): Notification[] {
        return this.repository.findAllByOrderByNotificationDateTimeDesc();
    }
}