# Generated by Django 5.0.4 on 2024-05-03 18:12

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('task', '0002_alter_task_tag'),
    ]

    operations = [
        migrations.AlterField(
            model_name='tag',
            name='title',
            field=models.CharField(max_length=50, unique=True),
        ),
    ]
