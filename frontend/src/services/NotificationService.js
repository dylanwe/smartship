import Notification from "@/models/notifications/Notification";
import NotificationRepository from "backend/src/main/java/com/smartship/backend/app/repositories/NotificationRepository.java";
import { Service, Autowired } from "vue-class-component";

@Service
export default class NotificationService {
    @Autowired
    repository: NotificationRepository;

    constructor() {
        this.repository = new NotificationRepository();
    }
    public findByMessageContainsLetters(letters: string): Notification[] {
        return this.repository.findByMessageContainsLetters(letters);
    }

    public findAllInOrderFromNewestToOldest(): Notification[] {
        return this.repository.findAllByOrderByNotificationDateTimeDesc();
    }
}