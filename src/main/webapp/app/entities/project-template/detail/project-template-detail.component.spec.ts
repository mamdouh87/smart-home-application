import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProjectTemplateDetailComponent } from './project-template-detail.component';

describe('ProjectTemplate Management Detail Component', () => {
  let comp: ProjectTemplateDetailComponent;
  let fixture: ComponentFixture<ProjectTemplateDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProjectTemplateDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ projectTemplate: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ProjectTemplateDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProjectTemplateDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load projectTemplate on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.projectTemplate).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
