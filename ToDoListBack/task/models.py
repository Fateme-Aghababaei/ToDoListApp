from django.db import models
from django.contrib.auth.models import User


class Tag(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    title = models.CharField(max_length=50, unique=True)

    def __str__(self) -> str:
        return f"{self.user.username}: {self.title}"
    

class Task(models.Model):
    PRIORITY_CHOICES = [(1, 'کم'), (2, 'متوسط'), (3, 'زیاد'), (0, 'مشخص‌نشده')]
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    title = models.CharField(max_length=200)
    description = models.TextField(null=True, blank=True)
    tags = models.ManyToManyField(Tag)
    due_date = models.DateField(null=True, blank=True)
    is_completed = models.BooleanField(default=False)
    priority = models.SmallIntegerField(choices=PRIORITY_CHOICES, default=0)

    def __str__(self) -> str:
        return f"{self.user.username}: {self.title}"